package org.digitalLibrary.Adapter;

import org.digitalLibrary.Mappers.Input.MembershipInputMapper;
import org.digitalLibrary.Services.MembershipService;
import org.digitalLibrary.entities.input.MembershipInputEntity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.model.MembershipModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MembershipAdapter {

    @Autowired
    private final MembershipService membershipService;
    private final MembershipInputMapper membershipInputMapper;

    public MembershipAdapter(MembershipService membershipService, MembershipInputMapper membershipInputMapper) {
        this.membershipService = membershipService;
        this.membershipInputMapper = membershipInputMapper;
    }
    public MembershipModel addmembership(MembershipInputEntity membershipInputEntity, int amountpaid) {
        return this.membershipService.addmembership(this.membershipInputMapper.maptoModel(membershipInputEntity), amountpaid);
    }
    public MembershipModel findbyId(long id){
        return this.membershipService.findbyId(id);
    }
    public List<MembershipModel> getallmembership(){
        return this.membershipService.getallmembership();
    }
    public List<MembershipModel> getMembershipbyStatus(MembershipStatus status){
        return this.membershipService.getmembershipByStatus(status);
    }
    public MembershipModel deletemembershipbyId(long id){
        return this.membershipService.deletemembershipbyId(id);
    }
    public MembershipModel update(MembershipInputEntity membershipInputEntity){
        return this.membershipService.update(this.membershipInputMapper.maptoModel(membershipInputEntity));
    }
    public MembershipModel updatemembershipStatus(long membershipId, MembershipStatus status){
        return this.membershipService.updatestatus(membershipId, status);
    }



}
