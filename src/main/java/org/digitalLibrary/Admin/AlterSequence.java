package org.digitalLibrary.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AlterSequence {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostMapping("/alter-sequence")
    public ResponseEntity<String> alterr(){
        jdbcTemplate.execute("ALTER SEQUENCE book_seq RESTART WITH 1");
        return ResponseEntity.ok("Sequence restarted form 1");
    }
}
