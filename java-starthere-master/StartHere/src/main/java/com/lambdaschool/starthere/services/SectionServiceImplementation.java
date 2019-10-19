package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.Section;
import com.lambdaschool.starthere.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service(value = "sectionService")
public class SectionServiceImplementation implements SectionService {

    @Autowired
    private SectionRepository sectionRepos;

    @Override
    public ArrayList<Section> findAll(Pageable pageable) {
        ArrayList<Section> list = new ArrayList<>();
        sectionRepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Section update(Section section, long id) {
        Section currentSection = sectionRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (section.getName() != null)
        {
            currentSection.setName(section.getName());
        }

        return sectionRepos.save(currentSection);
    }


}
