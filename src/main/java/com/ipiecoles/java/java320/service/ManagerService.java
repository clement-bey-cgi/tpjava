package com.ipiecoles.java.java320.service;

import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.repository.ManagerRepository;
import com.ipiecoles.java.java320.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    public Manager findOneWithEquipeById(Long id){
        return managerRepository.findOneWithEquipeById(id);
    }

    public void deleteTechniciens(Long idManager, Long idTechnicien) {
        Manager m = managerRepository.findOne(idManager);
        if(m == null){
            throw new EntityNotFoundException("");
        }
        Technicien t = technicienRepository.findOne(idTechnicien);
        if(t == null){
            throw new EntityNotFoundException("");
        }

        m.getEquipe().remove(t);
        managerRepository.save(m);

        t.setManager(null);
        technicienRepository.save(t);
    }

    public Technicien addTechniciens(Long idManager, String matricule) {
        Manager m = managerRepository.findOne(idManager);
        if(m == null){
            throw new EntityNotFoundException("Impossible de trouver le manager d'identifiant " + idManager);
        }
        Technicien t = technicienRepository.findByMatricule(matricule);
        if(t == null){
            throw new EntityNotFoundException("Impossible de trouver le technicien de matricule " + matricule);
        }

        if(t.getManager() != null){
            throw new IllegalArgumentException("Le technicien de matricule " + matricule + " a déjà un manager : " + t.getManager().getPrenom() + " " + t.getManager().getNom()
            + " (matricule " + t.getManager().getMatricule() + ")");
        }

        m.getEquipe().add(t);
        m = managerRepository.save(m);

        t.setManager(m);
        technicienRepository.save(t);

        return t;
    }

	public Manager saveThisManager(Manager managerToPersist) {
		Manager createdManager = managerRepository.save(managerToPersist);
		return createdManager;
	}

	public Manager findOne(Long id) {
		return managerRepository.findOne(id);
	}

	public Manager updateThisManager(Manager actualManager) {
		return managerRepository.save(actualManager);
	}
}
