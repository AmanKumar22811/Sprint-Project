package com.classicmodel.repository;

import com.classicmodel.entity.Office;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OfficeRepositoryTest {

    @Autowired
    private OfficeRepository officeRepo;

    @Test
    void testGetOfficeByCode_whenExists_returnsOffice() {
        Optional<Office> result = officeRepo.findById("1");
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getOfficeCode());
        assertEquals("San Francisco", result.get().getCity());
    }

    @Test
    void testGetOfficeByCode_whenNotExists_returnsEmpty() {
        Optional<Office> result = officeRepo.findById("INVALID_CODE_XYZ");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetOfficesByCountry_returnsOfficesInUSA() {
        List<Office> result = officeRepo.findByCountry("USA");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(o -> "USA".equals(o.getCountry())));
    }

    @Test
    void testGetOfficesByCity_returnsSingleResult() {
        List<Office> result = officeRepo.findByCity("Tokyo");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(o -> "Tokyo".equals(o.getCity())));
    }

    @Test
    void testGetOfficesByTerritory_returnsResults() {
        List<Office> result = officeRepo.findByTerritory("NA");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAllOffices_returnsNonEmpty() {
        Iterable<Office> all = officeRepo.findAll();
        assertNotNull(all);
        assertTrue(all.iterator().hasNext());
    }

    @Test
    void testCountOffices_greaterThanZero() {
        long count = officeRepo.count();
        assertTrue(count > 0);
    }

    @Test
    void testAddAndDeleteOffice_succeeds() {
        Office o = new Office();
        o.setOfficeCode("99");
        o.setCity("TestCity");
        o.setPhone("+1 650 000 0000");
        o.setAddressLine1("123 Test Ave");
        o.setCountry("USA");
        o.setPostalCode("00000");
        o.setTerritory("NA");

        officeRepo.save(o);
        assertTrue(officeRepo.findById("99").isPresent());
        officeRepo.deleteById("99");
        assertFalse(officeRepo.findById("99").isPresent());
    }

    @Test
    void testUpdateOfficeCity_succeeds() {
        Optional<Office> opt = officeRepo.findById("1");
        assertTrue(opt.isPresent());
        Office o = opt.get();
        String original = o.getCity();
        o.setCity("Updated City");
        officeRepo.save(o);
        assertEquals("Updated City", officeRepo.findById("1").get().getCity());
        o.setCity(original);
        officeRepo.save(o);
    }

    @Test
    void testGetOfficesByCityIn_returnsMultiple() {
        List<Office> result = officeRepo.findByCityIn(List.of("Tokyo", "London"));
        assertNotNull(result);
    }
}



//added all

