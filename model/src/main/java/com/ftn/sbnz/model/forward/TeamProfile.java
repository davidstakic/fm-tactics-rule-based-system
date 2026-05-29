package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.AttackType;
import com.ftn.sbnz.model.enums.MidfieldQuality;
import com.ftn.sbnz.model.enums.PhysicalProfile;

public class TeamProfile {
    private Double teamStrength;
    private String formLast5Matches; // npr. "W-W-D-L-W"
    private Integer tacticalFitness;
    private PhysicalProfile physicalProfile;
    private MidfieldQuality midfieldQuality;
    private Boolean highLineCapability;
    private AttackType attackType;

    public TeamProfile() {
    }

    public TeamProfile(Double teamStrength, String formLast5Matches, Integer tacticalFitness,
            PhysicalProfile physicalProfile, MidfieldQuality midfieldQuality,
            Boolean highLineCapability, AttackType attackType) {
        this.teamStrength = teamStrength;
        this.formLast5Matches = formLast5Matches;
        this.tacticalFitness = tacticalFitness;
        this.physicalProfile = physicalProfile;
        this.midfieldQuality = midfieldQuality;
        this.highLineCapability = highLineCapability;
        this.attackType = attackType;
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

    @Override
    public String toString() {
        return "TeamProfile{" +
                "teamStrength=" + teamStrength +
                ", formLast5Matches='" + formLast5Matches + '\'' +
                ", tacticalFitness=" + tacticalFitness +
                ", physicalProfile=" + physicalProfile +
                ", midfieldQuality=" + midfieldQuality +
                ", highLineCapability=" + highLineCapability +
                ", attackType=" + attackType +
                '}';
    }

}
