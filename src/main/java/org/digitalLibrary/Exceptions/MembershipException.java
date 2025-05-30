package org.digitalLibrary.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.digitalLibrary.model.MembershipModel;

@Slf4j
public class MembershipException extends  RuntimeException{
    private MembershipModel membershipModel;
    public MembershipException(long id){
        super("MembershipAlreadyexists");
        log.error("Membership with id {} already exists with user id {}", id, membershipModel.getUser().getUserid());
    }

    public MembershipException(String message){
        super(message);
        log.error(message);
    }

}
