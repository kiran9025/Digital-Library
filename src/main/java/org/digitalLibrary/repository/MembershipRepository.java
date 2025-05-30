package org.digitalLibrary.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.digitalLibrary.Exceptions.MembershipAlreadyExistsExcepton;
import org.digitalLibrary.Exceptions.MembershipException;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.Mappers.output.MembershipOutputMappers;
import org.digitalLibrary.entities.output.MembershipOutputEntity;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plans;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.MembershipModel;
import org.digitalLibrary.model.PaymentModel;
import org.digitalLibrary.repository.jpa.MembershipJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MembershipRepository {
    @Autowired
    private final MembershipJpaRepository membershipJpaRepository;
    private final MembershipOutputMappers membershipOutputMappers;

    public MembershipRepository(MembershipJpaRepository membershipJpaRepository, MembershipOutputMappers membershipOutputMappers) {
        this.membershipJpaRepository = membershipJpaRepository;
        this.membershipOutputMappers = membershipOutputMappers;
    }
    public MembershipModel createMembership(MembershipModel membershipModel, int amountpaid){
        if(amountpaid != membershipModel.getPlantype().getPrice()){
            throw new IllegalArgumentException("Amount for the plan is incorrect");
        }
        PaymentModel payment = new PaymentModel();
        payment.setAmountpaid(amountpaid);
        payment.setPaymentDate(LocalDate.now());
        payment.setSuccessful(true);

        MembershipModel membership  = new MembershipModel();
        membership.setMembershipid(membershipModel.getMembershipid());
        membership.setUser(membershipModel.getUser());
        membership.setPlantype(membershipModel.getPlantype());
        membership.setPaymentOutputEntity(membershipModel.getPaymentOutputEntity());
        membership.setStartdate(membership.getStartdate());
        membership.setStatus(membershipModel.getStatus());
        membership.setEnddate(
                LocalDate.now()
                        .plusMonths(membershipModel.getPlantype().getDurationinMonhts())
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );
        membership.setActive(true);
        MembershipOutputEntity membershipOutputEntity = this.membershipOutputMappers.mapfromModel(membership);
        MembershipOutputEntity savedentity = this.membershipJpaRepository.save(membershipOutputEntity);
        return this.membershipOutputMappers.maptoModel(savedentity);

    }

    public MembershipModel findbyId(long membershipid){
        MembershipOutputEntity membershipOutputEntity = this.membershipJpaRepository.findById(membershipid)
                .orElseThrow(() -> new ResourcenotFoundException(MembershipModel.class, "membershipID", String.valueOf(membershipid)));
        return this.membershipOutputMappers.maptoModel(membershipOutputEntity);
    }
    public List<MembershipModel> getallusers(){
        List<MembershipOutputEntity> all = this.membershipJpaRepository.findAll();
        return all.stream()
                .map(membershipOutputMappers::maptoModel)
                .collect(Collectors.toList());
    }

    public List<MembershipModel> findByStatus(MembershipStatus status){
        List<MembershipOutputEntity> list = this.membershipJpaRepository.findByStatus(status);
        return list.stream()
                .map(membershipOutputMappers::maptoModel)
                .collect(Collectors.toList());

    }
    public MembershipModel deletebyId(long id){
        Optional<MembershipOutputEntity> option = this.membershipJpaRepository.findById(id);

        if(option.isPresent()){
            MembershipOutputEntity membershipOutputEntity = option.get();
            this.membershipJpaRepository.deleteById(id);
            return this.membershipOutputMappers.maptoModel(membershipOutputEntity);
        }else{
            throw new ResourcenotFoundException(MembershipModel.class, "id", String.valueOf(id));
        }
    }

    public MembershipModel update(MembershipModel membershipModel){
        MembershipOutputEntity entity = this.membershipOutputMappers.mapfromModel(membershipModel);
        this.membershipJpaRepository.save(entity);
        return this.membershipOutputMappers.maptoModel(entity);

    }
    public MembershipModel updateMembershipStatus(long membershipid, MembershipStatus newstatus){
        MembershipOutputEntity membershipOutputEntity = this.membershipJpaRepository.findById(membershipid)
                .orElseThrow(() -> new ResourcenotFoundException(MembershipModel.class, "membershipID", String.valueOf(membershipid)));
        MembershipStatus currentstatus = membershipOutputEntity.getStatus();
        if((currentstatus == MembershipStatus.Expired || currentstatus == MembershipStatus.Paused) && newstatus == MembershipStatus.Active) {
            throw new MembershipException("Status cannot be updated from Expired or Paused to Active. Please purchase a new membership.");
        }
        membershipOutputEntity.setStatus(newstatus);
        membershipOutputEntity = this.membershipJpaRepository.save(membershipOutputEntity);
        return this.membershipOutputMappers.maptoModel(membershipOutputEntity);
    }



}




