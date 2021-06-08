/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.incidents;

/**
 * Represent an entry into the incident table model.
 *
 * @author Leonard
 */
public class IncidentEntry {

    private final IncidentInfo incident;
    private final int rows;
    private final Devider deviderType;

    public IncidentEntry(IncidentInfo incident) {
        this(incident, Devider.NONE);
    }

    public IncidentEntry(IncidentInfo incident, Devider deviderType) {
        this.incident = incident;
        this.rows = incident.getCars().size();
        this.deviderType = deviderType;
    }

    public IncidentInfo getIncident() {
        return incident;
    }

    public int getRows() {
        return rows;
    }

    public Devider getDeviderType() {
        return deviderType;
    }

    public enum Devider {
        NONE,
        PRACTICE,
        QUALIFYING,
        RACE;
    }

}
