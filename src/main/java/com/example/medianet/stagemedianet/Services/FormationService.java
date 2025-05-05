package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Formation;
import com.example.medianet.stagemedianet.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;

    public Formation save(Formation formation) {
        return formationRepository.save(formation);
    }
    public List<Formation> findAll() {
        return formationRepository.findAll();
    }
    public Optional<Formation> findById(Long id) {
        return formationRepository.findById(id);
    }

    public void deleteById(Long id) {
        formationRepository.deleteById(id);
    }
    public Formation updateFormation(Long id, Formation updatedFormation) {
        return formationRepository.findById(id).map(existingFormation -> {
            existingFormation.setTitre(updatedFormation.getTitre());
            existingFormation.setDescription(updatedFormation.getDescription());
            return formationRepository.save(existingFormation);
        }).orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'id : " + id));
    }

}
