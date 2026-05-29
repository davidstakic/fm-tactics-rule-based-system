package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.enums.AttackType;
import com.ftn.sbnz.model.enums.MidfieldQuality;
import com.ftn.sbnz.model.enums.PhysicalProfile;
import com.ftn.sbnz.model.forward.TeamProfile;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TeamProfileRequest {
    @NotNull(message = "Team strength is required.")
    @DecimalMin(value = "1.0", message = "Team strength must be at least 1.0.")
    @DecimalMax(value = "5.0", message = "Team strength must be at most 5.0.")
    private Double teamStrength;

    @NotBlank(message = "Last five matches form is required.")
    @Pattern(regexp = "^[WwDdLl](?:-[WwDdLl]){4}$", message = "Last five matches form must contain exactly five results, for example W-W-D-L-W.")
    private String formLast5Matches;

    @NotNull(message = "Tactical familiarity is required.")
    @Min(value = 0, message = "Tactical familiarity must be at least 0.")
    @Max(value = 100, message = "Tactical familiarity must be at most 100.")
    private Integer tacticalFitness;

    @NotNull(message = "Physical profile is required.")
    private PhysicalProfile physicalProfile;

    @NotNull(message = "Midfield quality is required.")
    private MidfieldQuality midfieldQuality;

    @NotNull(message = "High defensive line ability is required.")
    private Boolean highLineCapability;

    @NotNull(message = "Attacking type is required.")
    private AttackType attackType;

    public TeamProfileRequest() {
    }

    public Double getTeamStrength() {
        return teamStrength;
    }

    public void setTeamStrength(Double teamStrength) {
        this.teamStrength = teamStrength;
    }

    public String getFormLast5Matches() {
        return formLast5Matches;
    }

    public void setFormLast5Matches(String formLast5Matches) {
        this.formLast5Matches = formLast5Matches;
    }

    public Integer getTacticalFitness() {
        return tacticalFitness;
    }

    public void setTacticalFitness(Integer tacticalFitness) {
        this.tacticalFitness = tacticalFitness;
    }

    public PhysicalProfile getPhysicalProfile() {
        return physicalProfile;
    }

    public void setPhysicalProfile(PhysicalProfile physicalProfile) {
        this.physicalProfile = physicalProfile;
    }

    public MidfieldQuality getMidfieldQuality() {
        return midfieldQuality;
    }

    public void setMidfieldQuality(MidfieldQuality midfieldQuality) {
        this.midfieldQuality = midfieldQuality;
    }

    public Boolean getHighLineCapability() {
        return highLineCapability;
    }

    public void setHighLineCapability(Boolean highLineCapability) {
        this.highLineCapability = highLineCapability;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public TeamProfile toTeamProfile() {
        return new TeamProfile(
                teamStrength,
                formLast5Matches.toUpperCase(),
                tacticalFitness,
                physicalProfile,
                midfieldQuality,
                highLineCapability,
                attackType);
    }
}
