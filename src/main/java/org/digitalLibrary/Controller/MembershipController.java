package org.digitalLibrary.Controller;

import org.digitalLibrary.Adapter.MembershipAdapter;
import org.digitalLibrary.Exceptions.MembershipException;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.entities.input.MembershipInputEntity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.model.MembershipModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membership")
public class MembershipController {

    @Autowired
    private final MembershipAdapter membershipAdapter;

    public MembershipController(MembershipAdapter membershipAdapter) {
        this.membershipAdapter = membershipAdapter;
    }
    @PostMapping("/add")
    public ResponseEntity<MembershipModel> addMembership(@RequestBody MembershipInputEntity membershipInputEntity, @RequestParam int amountpaid){
        membershipInputEntity.setStatus(membershipInputEntity.getStatus());
        MembershipModel membershipModel = this.membershipAdapter.addmembership(membershipInputEntity,amountpaid);
        System.out.println(membershipInputEntity.getStatus());
        return new ResponseEntity<>(membershipModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipModel> findbyId(@PathVariable long id){
        try {
            return new ResponseEntity<>(this.membershipAdapter.findbyId(id), HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<MembershipModel> update(@RequestBody MembershipInputEntity membershipInputEntity){
        try {
            return new ResponseEntity<>(this.membershipAdapter.update(membershipInputEntity), HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getall")
    public List<MembershipModel> getallusers(){
        return this.membershipAdapter.getallmembership();
    }
    @GetMapping("/getallmembershipbystatus")
    public List<MembershipModel> getmembershipbyStatus(@RequestParam MembershipStatus status){
        return this.membershipAdapter.getMembershipbyStatus(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MembershipModel> deletemembership(@PathVariable long id){
        try{
            return new ResponseEntity<>(this.membershipAdapter.deletemembershipbyId(id), HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{membershipid}")
    public ResponseEntity<MembershipModel> updateStatus(@PathVariable long membershipid, @RequestParam MembershipStatus status){
        try{
            return new ResponseEntity<>(this.membershipAdapter.updatemembershipStatus(membershipid,status),HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
