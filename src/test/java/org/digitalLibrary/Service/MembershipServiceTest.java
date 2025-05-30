package org.digitalLibrary.Service;

import org.digitalLibrary.Services.MembershipService;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.MembershipModel;
import org.digitalLibrary.repository.MembershipRepository;
import org.junit.jupiter.api.*;
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
public class MembershipServiceTest {
    @Mock
    private MembershipRepository membershipRepository;
    @InjectMocks
    private MembershipService membershipService;

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
    void testaddmemership(){
        Mockito.when(membershipRepository.createMembership(membershipModel,199)).thenReturn(membershipModel);
        MembershipModel actual = this.membershipService.addmembership(membershipModel, 199);
        Assertions.assertEquals(actual, membershipModel);

    }

    @Test
    @DisplayName("findbyId should return MembershipModel")
    void testFindById() {
        when(membershipRepository.findbyId(1L)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipService.findbyId(1L);
        assertEquals(membershipModel, actualoutput);
    }

    @Test
    @DisplayName("getallmembership should return list of MembershipModels")
    void testGetAllMembership() {
        when(membershipRepository.getallusers()).thenReturn(membershipModelList);

        List<MembershipModel> actualoutput = membershipService.getallmembership();
        assertEquals(membershipModelList, actualoutput);
    }

    @Test
    @DisplayName("getmembershipByStatus should return list filtered by status")
    void testGetMembershipByStatus() {
        when(membershipRepository.findByStatus(MembershipStatus.Active)).thenReturn(membershipModelList);

        List<MembershipModel> actualoutput = membershipService.getmembershipByStatus(MembershipStatus.Active);
        assertEquals(membershipModelList, actualoutput);
    }

    @Test
    @DisplayName("deletemembershipbyId should delete and return MembershipModel")
    void testDeleteMembershipById() {
        when(membershipRepository.deletebyId(membershipid)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipService.deletemembershipbyId(membershipid);
        assertEquals(membershipModel, actualoutput);
    }

    @Test
    @DisplayName("update should update and return MembershipModel")
    void testUpdateMembership() {
        when(membershipRepository.update(membershipModel)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipService.update(membershipModel);
        assertEquals(membershipModel, actualoutput);
    }

    @Test
    @DisplayName("updatestatus should update status and return MembershipModel")
    void testUpdateStatus() {
        when(membershipRepository.updateMembershipStatus(membershipid, MembershipStatus.Active)).thenReturn(membershipModel);

        MembershipModel actualoutput = membershipService.updatestatus(membershipid, MembershipStatus.Active);
        assertEquals(membershipModel, actualoutput);
    }
}


