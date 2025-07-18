package com.example.medianet.stagemedianet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name="Formation")
@Getter
@Setter

public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String domaine;  // Exemple : "Java", "Leadership", "Communication"
    private String niveau;   // Exemple : "Débutant", "Intermédiaire", "Avancé"
    private String cible;    // Exemple : "Développeur", "Manager", "RH"
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Column(name = "places_disponibles")
    private Integer placesDisponibles = 0;

    @ManyToMany
    @JoinTable(
            name = "formation_utilisateur",
            joinColumns = @JoinColumn(name = "formation_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    private List<User> utilisateursReserves = new ArrayList<>();

    public List<User> getUtilisateursReserves() {
        return utilisateursReserves;
    }

    public void setUtilisateursReserves(List<User> utilisateursReserves) {
        this.utilisateursReserves = utilisateursReserves;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

    public Formation(Long id, String titre, String description, String domaine, String niveau, String cible, LocalDate dateDebut, LocalDate dateFin, Integer placesDisponibles, List<User> utilisateursReserves) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.domaine = domaine;
        this.niveau = niveau;
        this.cible = cible;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.placesDisponibles = placesDisponibles;
        this.utilisateursReserves = utilisateursReserves;
    }

    public Formation(Long id, String titre, String description, LocalDate dateDebut, LocalDate dateFin, int placesDisponibles) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.placesDisponibles = placesDisponibles;
    }

    public Integer getPlacesDisponibles() {
        return placesDisponibles != null ? placesDisponibles : 0;
    }


    public void setPlacesDisponibles(Integer placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }


    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Formation(Long id, String titre, String description, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Formation(Long id, String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
    }

    public Formation() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
