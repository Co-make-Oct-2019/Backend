package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Section;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface SectionService {
    ArrayList<Section> findAll(Pageable pageable);

    Section update(Section section, long id);
}
