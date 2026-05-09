package com.ftn.sbnz.model;

public class Level1Facts {
    // Relativna snaga tima
    private Boolean isFavorite; // jačina_tima - jačina_protivnika > 0.8
    private Boolean isUnderdog; // jačina_protivnika - jačina_tima > 0.8
    private Boolean isEvenlyMatched; // apsolutna razlika ≤ 0.8

    // Forma tima
    private Boolean hasGoodForm; // ≥ 3 pobede u poslednjih 5 mečeva
    private Boolean hasBadForm; // ≤ 1 pobeda u poslednjih 5 mečeva
    private Boolean hasNeutralForm; // 2 pobede u poslednjih 5 mečeva

    // Taktička uigranost
    private Boolean hasHighFitness; // ≥ 70%
    private Boolean hasMediumFitness; // 40% - 69%
    private Boolean hasLowFitness; // < 40%

    // Kontekstualne situacije meča
    private Boolean isDifficultAwayMatch; // AUTSAJDER + Gost + Visoka važnost
    private Boolean mustWin; // Kup + Visoka važnost
    private Boolean hasDominantPosition; // FAVORIT + Domaćin
    private Boolean canDrawMatch; // RAVNOPRAVAN + Liga + Srednja važnost
    private Boolean isCautiousMatch; // AUTSAJDER + Visoka važnost

    // Karakteristike protivnika
    private Boolean hasCounterOpportunity; // Protivnik: Spori odbrambeni ili Ranjivi po bokovima
    private Boolean hasAerialOpportunity; // Protivnik: Loša skok igra
    private Boolean hasShootingOpportunity; // Protivnik: Nesiguran golman
    private Boolean isAggressiveOpponent; // AGRESIVAN_PROTIVNIK
    private Boolean isPassiveOpponent; // PASIVAN_PROTIVNIK

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Boolean getIsUnderdog() {
        return isUnderdog;
    }

    public void setIsUnderdog(Boolean isUnderdog) {
        this.isUnderdog = isUnderdog;
    }

    public Boolean getIsEvenlyMatched() {
        return isEvenlyMatched;
    }

    public void setIsEvenlyMatched(Boolean isEvenlyMatched) {
        this.isEvenlyMatched = isEvenlyMatched;
    }

    public Boolean getHasGoodForm() {
        return hasGoodForm;
    }

    public void setHasGoodForm(Boolean hasGoodForm) {
        this.hasGoodForm = hasGoodForm;
    }

    public Boolean getHasBadForm() {
        return hasBadForm;
    }

    public void setHasBadForm(Boolean hasBadForm) {
        this.hasBadForm = hasBadForm;
    }

    public Boolean getHasNeutralForm() {
        return hasNeutralForm;
    }

    public void setHasNeutralForm(Boolean hasNeutralForm) {
        this.hasNeutralForm = hasNeutralForm;
    }

    public Boolean getHasHighFitness() {
        return hasHighFitness;
    }

    public void setHasHighFitness(Boolean hasHighFitness) {
        this.hasHighFitness = hasHighFitness;
    }

    public Boolean getHasMediumFitness() {
        return hasMediumFitness;
    }

    public void setHasMediumFitness(Boolean hasMediumFitness) {
        this.hasMediumFitness = hasMediumFitness;
    }

    public Boolean getHasLowFitness() {
        return hasLowFitness;
    }

    public void setHasLowFitness(Boolean hasLowFitness) {
        this.hasLowFitness = hasLowFitness;
    }

    public Boolean getIsDifficultAwayMatch() {
        return isDifficultAwayMatch;
    }

    public void setIsDifficultAwayMatch(Boolean isDifficultAwayMatch) {
        this.isDifficultAwayMatch = isDifficultAwayMatch;
    }

    public Boolean getMustWin() {
        return mustWin;
    }

    public void setMustWin(Boolean mustWin) {
        this.mustWin = mustWin;
    }

    public Boolean getHasDominantPosition() {
        return hasDominantPosition;
    }

    public void setHasDominantPosition(Boolean hasDominantPosition) {
        this.hasDominantPosition = hasDominantPosition;
    }

    public Boolean getCanDrawMatch() {
        return canDrawMatch;
    }

    public void setCanDrawMatch(Boolean canDrawMatch) {
        this.canDrawMatch = canDrawMatch;
    }

    public Boolean getIsCautiousMatch() {
        return isCautiousMatch;
    }

    public void setIsCautiousMatch(Boolean isCautiousMatch) {
        this.isCautiousMatch = isCautiousMatch;
    }

    public Boolean getHasCounterOpportunity() {
        return hasCounterOpportunity;
    }

    public void setHasCounterOpportunity(Boolean hasCounterOpportunity) {
        this.hasCounterOpportunity = hasCounterOpportunity;
    }

    public Boolean getHasAerialOpportunity() {
        return hasAerialOpportunity;
    }

    public void setHasAerialOpportunity(Boolean hasAerialOpportunity) {
        this.hasAerialOpportunity = hasAerialOpportunity;
    }

    public Boolean getHasShootingOpportunity() {
        return hasShootingOpportunity;
    }

    public void setHasShootingOpportunity(Boolean hasShootingOpportunity) {
        this.hasShootingOpportunity = hasShootingOpportunity;
    }

    public Boolean getIsAggressiveOpponent() {
        return isAggressiveOpponent;
    }

    public void setIsAggressiveOpponent(Boolean isAggressiveOpponent) {
        this.isAggressiveOpponent = isAggressiveOpponent;
    }

    public Boolean getIsPassiveOpponent() {
        return isPassiveOpponent;
    }

    public void setIsPassiveOpponent(Boolean isPassiveOpponent) {
        this.isPassiveOpponent = isPassiveOpponent;
    }

    @Override
    public String toString() {
        return "Level1Facts{" +
                "isFavorite=" + isFavorite +
                ", isUnderdog=" + isUnderdog +
                ", isEvenlyMatched=" + isEvenlyMatched +
                ", hasGoodForm=" + hasGoodForm +
                ", hasBadForm=" + hasBadForm +
                ", hasNeutralForm=" + hasNeutralForm +
                ", hasHighFitness=" + hasHighFitness +
                ", hasMediumFitness=" + hasMediumFitness +
                ", hasLowFitness=" + hasLowFitness +
                ", isDifficultAwayMatch=" + isDifficultAwayMatch +
                ", mustWin=" + mustWin +
                ", hasDominantPosition=" + hasDominantPosition +
                ", canDrawMatch=" + canDrawMatch +
                ", isCautiousMatch=" + isCautiousMatch +
                ", hasCounterOpportunity=" + hasCounterOpportunity +
                ", hasAerialOpportunity=" + hasAerialOpportunity +
                ", hasShootingOpportunity=" + hasShootingOpportunity +
                ", isAggressiveOpponent=" + isAggressiveOpponent +
                ", isPassiveOpponent=" + isPassiveOpponent +
                '}';
    }
}
