package org.digitalLibrary.Adapter;

import org.digitalLibrary.Mappers.Input.MembershipInputMapper;
import org.digitalLibrary.Services.MembershipService;
import org.digitalLibrary.entities.input.MembershipInputEntity;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.MembershipModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembershipAdapterTest {
    @Mock
    private MembershipService membershipService;
    @Mock
    private MembershipInputMapper membershipInputMapper;
    @InjectMocks
    private MembershipAdapter membershipAdapter;

    private static long membershipid;
    private static MembershipInputEntity membershipInputEntity;
    private static MembershipModel membershipModel;
    private static UserOutputentity userOutputentity;
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
                .status(MembershipStatus.Active)
                .paymentOutputEntity(payment)
                .build();
        membershipModelList = new ArrayList<>();
        membershipModelList.add(membershipModel);
    }
    @Test
    @DisplayName("addmembership should return membershipmodel")
    void testaddmembership(){
        Mockito.when(membershipInputMapper.maptoModel(membershipInputEntity)).thenReturn(membershipModel);
        Mockito.when(membershipService.addmembership(membershipModel, 199)).thenReturn(membershipModel);
        MembershipModel actual = this.membershipAdapter.addmembership(membershipInputEntity, 199);
        Assertions.assertEquals(actual, membershipModel);
    }

    @Test
    @DisplayName("findbyId should return MembershipModel")
    void testFindById() {
        when(membershipService.findbyId(membershipid)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipAdapter.findbyId(membershipid);
        assertEquals(membershipModel, actualoutput);
    }

    @Test
    @DisplayName("getallmembership should return list of MembershipModels")
    void testGetAllMemberships() {
        when(membershipService.getallmembership()).thenReturn(membershipModelList);

        List<MembershipModel> actualoutput = membershipAdapter.getallmembership();
        assertEquals(membershipModelList, actualoutput);
    }

    @Test
    @DisplayName("getMembershipByStatus should return filtered list by status")
    void testGetMembershipByStatus() {
        when(membershipService.getmembershipByStatus(MembershipStatus.Active)).thenReturn(membershipModelList);

        List<MembershipModel> actualoutput = membershipAdapter.getMembershipbyStatus(MembershipStatus.Active);
        assertEquals(membershipModelList, actualoutput);
    }

    @Test
    @DisplayName("deletemembershipbyId should return deleted MembershipModel")
    void testDeleteMembershipById() {
        when(membershipService.deletemembershipbyId(membershipid)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipAdapter.deletemembershipbyId(membershipid);
        assertEquals(membershipModel, actualoutput);
    }

    @Test
    @DisplayName("update should return updated MembershipModel")
    void testUpdate() {
        when(membershipInputMapper.maptoModel(membershipInputEntity)).thenReturn(membershipModel);
        when(membershipService.update(membershipModel)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipAdapter.update(membershipInputEntity);
        assertEquals(membershipModel, actualoutput);
    }

    @Test
    @DisplayName("updatemembershipStatus should return updated MembershipModel")
    void testUpdateStatus() {
        when(membershipService.updatestatus(membershipid, MembershipStatus.Active)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipAdapter.updatemembershipStatus(membershipid, MembershipStatus.Active);
        assertEquals(membershipModel, actualoutput);
    }


}
