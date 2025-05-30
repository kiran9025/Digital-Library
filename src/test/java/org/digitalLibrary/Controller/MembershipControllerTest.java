package org.digitalLibrary.Controller;

import org.digitalLibrary.Adapter.MembershipAdapter;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.entities.input.MembershipInputEntity;
import org.digitalLibrary.entities.input.UserInputentity;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.MembershipModel;
import org.digitalLibrary.model.UserModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MembershipControllerTest {
    @Mock
    private MembershipAdapter membershipAdapter;
    @InjectMocks
    private MembershipController membershipController;
    private static MembershipInputEntity membershipInputEntity;
    private static MembershipModel membershipModel;
    private static UserOutputentity userOutputentity;
    private static long membershipid;
    private static List<MembershipModel> membershipModelList;
    private static Instant startdate;
    private static Instant enddate;

    @BeforeAll
    static void setup(){
        membershipid = 1L;
        startdate = Instant.now();
        enddate = Instant.now().plusSeconds(365*24*60*60);
        PaymentOutputEntity payment = PaymentOutputEntity.builder()
                .amountpaid(199)
                .successful(true)
                .payementdate(LocalDate.now())
                .build();
        userOutputentity  = UserOutputentity.builder()
                .userid(1L)
                .firstname("kiran")
                .lastname("kumar")
                .email("kiran2002@gamail.com")
                .dateofbirth(Instant.now())
                .phonenumber(1234567788)
                .build();
        membershipInputEntity = MembershipInputEntity.builder()
                .user(userOutputentity)
                .membershipid(membershipid)
                .startdate(startdate)
                .enddate(enddate)
                .plantype(Plantype.SIX_MONTHS)
                .paymentOutputEntity(payment)
                .status(MembershipStatus.Active)
                .build();
        membershipModel = MembershipModel.builder()
                .user(userOutputentity)
                .membershipid(membershipid)
                .startdate(startdate)
                .enddate(enddate)
                .plantype(Plantype.SIX_MONTHS)
                .paymentOutputEntity(payment)
                .status(MembershipStatus.Active)
                .build();
        membershipModelList = new ArrayList<>();
        membershipModelList.add(membershipModel);


    }
    @Test
    @DisplayName("")
    void testaddmembership(){
        Mockito.when(membershipAdapter.addmembership(membershipInputEntity, 199)).thenReturn(membershipModel);
        ResponseEntity<MembershipModel> response = this.membershipController.addMembership(membershipInputEntity, 199);
        Assertions.assertEquals(membershipModel, response.getBody());
    }

    @Test
    @DisplayName("find by id should return membershipmodel if id is present")
    void testfindById(){
        Mockito.when(membershipAdapter.findbyId(membershipid)).thenReturn(membershipModel);
        ResponseEntity<MembershipModel> response = membershipController.findbyId(membershipid);
        Assertions.assertEquals(membershipModel, response.getBody());
    }
    @Test
    @DisplayName("Update should return MembershipModel if update is successful")
    void testUpdateMembership_Success() {
        Mockito.when(membershipAdapter.update(membershipInputEntity)).thenReturn(membershipModel);
        ResponseEntity<MembershipModel> response = membershipController.update(membershipInputEntity);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(membershipModel, response.getBody());
    }

    @Test
    @DisplayName("Update should return BAD_REQUEST if membership not found")
    void testUpdateMembership_NotFound() {
        Mockito.when(membershipAdapter.update(membershipInputEntity)).thenThrow(new ResourcenotFoundException(MembershipController.class, "membershipid", String.valueOf(membershipid)));
        ResponseEntity<MembershipModel> response = membershipController.update(membershipInputEntity);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Get all memberships should return a list of MembershipModels")
    void testGetAllUsers() {
        Mockito.when(membershipAdapter.getallmembership()).thenReturn(membershipModelList);
        List<MembershipModel> result = membershipController.getallusers();
        Assertions.assertEquals(membershipModelList, result);
    }

    @Test
    @DisplayName("Get memberships by status should return list if status is valid")
    void testGetMembershipByStatus_Success() {
        Mockito.when(membershipAdapter.getMembershipbyStatus(MembershipStatus.Active)).thenReturn(membershipModelList);
        List<MembershipModel> result = membershipController.getmembershipbyStatus(MembershipStatus.Active);
        Assertions.assertEquals(membershipModelList, result);
    }


    @Test
    @DisplayName("Delete membership should return MembershipModel if id is present")
    void testDeleteMembership_Success() {
        Mockito.when(membershipAdapter.deletemembershipbyId(membershipid)).thenReturn(membershipModel);
        ResponseEntity<MembershipModel> response = membershipController.deletemembership(membershipid);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(membershipModel, response.getBody());
    }

    @Test
    @DisplayName("Delete membership should return BAD_REQUEST if id not found")
    void testDeleteMembership_NotFound() {
        Mockito.when(membershipAdapter.deletemembershipbyId(membershipid)).thenThrow(new ResourcenotFoundException(MembershipController.class,"Membership", String.valueOf(membershipid)));
        ResponseEntity<MembershipModel> response = membershipController.deletemembership(membershipid);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Update status should return MembershipModel if update successful")
    void testUpdateStatus_Success() {
        Mockito.when(membershipAdapter.updatemembershipStatus(membershipid, MembershipStatus.Active)).thenReturn(membershipModel);
        ResponseEntity<MembershipModel> response = membershipController.updateStatus(membershipid, MembershipStatus.Active);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(membershipModel, response.getBody());
    }

    @Test
    @DisplayName("Update status should return BAD_REQUEST if membership id not found")
    void testUpdateStatus_NotFound() {
        Mockito.when(membershipAdapter.updatemembershipStatus(membershipid, MembershipStatus.Active)).thenThrow(new ResourcenotFoundException(MembershipController.class,"Membership", String.valueOf(membershipid)));
        ResponseEntity<MembershipModel> response = membershipController.updateStatus(membershipid, MembershipStatus.Active);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


}
