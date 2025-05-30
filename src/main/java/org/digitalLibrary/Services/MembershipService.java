package org.digitalLibrary.Services;

import lombok.extern.slf4j.Slf4j;
import org.digitalLibrary.Exceptions.MembershipException;
import org.digitalLibrary.entities.output.MembershipOutputEntity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.model.MembershipModel;
import org.digitalLibrary.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MembershipService {
    @Autowired
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }
    public MembershipModel addmembership(MembershipModel membershipModel, int amountpaid){
       return this.membershipRepository.createMembership(membershipModel,amountpaid);
    }
    public MembershipModel findbyId(long id){
        return this.membershipRepository.findbyId(id);
    }
    public List<MembershipModel> getallmembership(){
        return this.membershipRepository.getallusers();
    }
    public List<MembershipModel> getmembershipByStatus(MembershipStatus status){
        return this.membershipRepository.findByStatus(status);
    }
    public MembershipModel deletemembershipbyId(long id){
        return this.membershipRepository.deletebyId(id);
    }
    public MembershipModel update(MembershipModel membershipModel){
        return this.membershipRepository.update(membershipModel);
    }
    public MembershipModel updatestatus(long membershipid, MembershipStatus status){
        return this.membershipRepository.updateMembershipStatus(membershipid,status);
    }


}
