package org.digitalLibrary.Repository;

import org.digitalLibrary.Exceptions.MembershipAlreadyExistsExcepton;
import org.digitalLibrary.Exceptions.MembershipException;
import org.digitalLibrary.Mappers.output.MembershipOutputMappers;
import org.digitalLibrary.entities.output.MembershipOutputEntity;
import org.digitalLibrary.entities.output.PaymentOutputEntity;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.enums.MembershipStatus;
import org.digitalLibrary.enums.Plans;
import org.digitalLibrary.enums.Plantype;
import org.digitalLibrary.model.MembershipModel;
import org.digitalLibrary.model.UserModel;
import org.digitalLibrary.repository.MembershipRepository;
import org.digitalLibrary.repository.jpa.MembershipJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MembershiRepositoryTest {
    @Mock
    private MembershipJpaRepository membershipJpaRepository;
    @Mock
    private MembershipOutputMappers membershipOutputMappers;
    @InjectMocks
    private MembershipRepository membershipRepository;

    private static MembershipModel membershipModel;
    private static MembershipOutputEntity membershipOutputEntity;
    private static UserOutputentity userOutputentity;
    private static UserModel userModel;
    private static long membershipid;
    private static List<MembershipModel> membershipModelList;
    private static Instant startdate;
    private static Instant enddate;

    @BeforeAll
    static void setup() {
        membershipid = 1L;
        startdate = Instant.now();
        enddate = Instant.now().plusSeconds(365 * 24 * 60 * 60);
        PaymentOutputEntity payment = PaymentOutputEntity.builder()
                .amountpaid(199)
                .successful(true)
                .payementdate(LocalDate.now())
                .build();
        userOutputentity = UserOutputentity.builder()
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
        membershipOutputEntity = MembershipOutputEntity.builder()
                .user(userOutputentity)
                .membershipid(membershipid)
                .startdate(startdate)
                .enddate(enddate)
                .status(MembershipStatus.Active)
                .paymentOutputEntity(payment)
                .plantype(Plantype.SIX_MONTHS)
                .build();
        membershipModelList = new ArrayList<>();
        membershipModelList.add(membershipModel);
    }
    @Test
    @DisplayName("Test Create Membership")
    void testCreateMembership() {
        Plantype plan = Plantype.SIX_MONTHS;

        MembershipOutputEntity membershipOutputEntity = new MembershipOutputEntity();
        membershipOutputEntity.setMembershipid(1L);
        membershipOutputEntity.setPlantype(plan);
        membershipOutputEntity.setActive(true);

        Mockito.when(membershipOutputMappers.mapfromModel(Mockito.any(MembershipModel.class)))
                .thenReturn(membershipOutputEntity);
        Mockito.when(membershipJpaRepository.save(Mockito.any(MembershipOutputEntity.class)))
                .thenReturn(membershipOutputEntity);
        Mockito.when(membershipOutputMappers.maptoModel(membershipOutputEntity))
                .thenReturn(membershipModel);
        MembershipModel result = membershipRepository.createMembership(membershipModel, plan.getPrice());

        assertNotNull(result);
        assertEquals(plan, result.getPlantype());

    }


    @Test
    @DisplayName("findbyId should return Membershipmodel")
    void testFindById() {
        when(membershipJpaRepository.findById(membershipid)).thenReturn(Optional.of(membershipOutputEntity));
        when(membershipOutputMappers.maptoModel(membershipOutputEntity)).thenReturn(membershipModel);
        MembershipModel found = membershipRepository.findbyId(membershipid);
        assertEquals(membershipModel, found);
    }

    @Test
    @DisplayName("FindbyId should throw ann Exception if id not found ")
    void testFindById_Negative() {
        when(membershipJpaRepository.findById(membershipid)).thenReturn(Optional.empty());
        assertThrows(ResourcenotFoundException.class, () -> membershipRepository.findbyId(membershipid));
    }

    @Test
    @DisplayName("getall users should return a list of membershipoutputEntity")
    void testGetAllUsers_Positive() {
        List<MembershipOutputEntity> entities = List.of(membershipOutputEntity);
        when(membershipJpaRepository.findAll()).thenReturn(entities);
        when(membershipOutputMappers.maptoModel(membershipOutputEntity)).thenReturn(membershipModel);

        List<MembershipModel> result = membershipRepository.getallusers();
        assertEquals(1, result.size());
        assertEquals(membershipModel, result.get(0));
    }


    @Test
    @DisplayName("findbyStatus should return list of  membershipmodel if status matched ")
    void testFindByStatus_Positive() {
        when(membershipJpaRepository.findByStatus(MembershipStatus.Active)).thenReturn(List.of(membershipOutputEntity));
        when(membershipOutputMappers.maptoModel(membershipOutputEntity)).thenReturn(membershipModel);

        List<MembershipModel> result = membershipRepository.findByStatus(MembershipStatus.Active);
        assertEquals(1, result.size());
        assertEquals(membershipModel, result.get(0));
    }


    @Test
    @DisplayName("deleteby id should return a membershipmodel if id found ")
    void testDeleteById() {
        when(membershipJpaRepository.findById(membershipid)).thenReturn(Optional.of(membershipOutputEntity));
        when(membershipOutputMappers.maptoModel(membershipOutputEntity)).thenReturn(membershipModel);

        MembershipModel deleted = membershipRepository.deletebyId(membershipid);
        verify(membershipJpaRepository, times(1)).deleteById(membershipid);
        assertEquals(membershipModel, deleted);
    }


    @Test
    @DisplayName("Delete by should throw an exception if id not found")
    void testDeleteById_Negative() {
        when(membershipJpaRepository.findById(membershipid)).thenReturn(Optional.empty());

        assertThrows(ResourcenotFoundException.class, () -> membershipRepository.deletebyId(membershipid));
    }


    @Test
    @DisplayName("update should return a membershipmodel ")
    void testUpdate_Positive() {
        when(membershipOutputMappers.mapfromModel(membershipModel)).thenReturn(membershipOutputEntity);
        when(membershipJpaRepository.save(membershipOutputEntity)).thenReturn(membershipOutputEntity);
        when(membershipOutputMappers.maptoModel(membershipOutputEntity)).thenReturn(membershipModel);

        MembershipModel updated = membershipRepository.update(membershipModel);
        assertEquals(membershipModel, updated);
    }

    @Test
    @DisplayName("Updateby status should return a membershipmodel ")
    void testUpdateStatus_Positive() {
        when(membershipJpaRepository.findById(membershipid)).thenReturn(Optional.of(membershipOutputEntity));
        when(membershipJpaRepository.save(Mockito.any())).thenReturn(membershipOutputEntity);
        when(membershipOutputMappers.maptoModel(membershipOutputEntity)).thenReturn(membershipModel);

        MembershipModel updated = membershipRepository.updateMembershipStatus(membershipid, MembershipStatus.Paused);
        assertEquals(membershipModel, updated);
    }



}



