package com.example.spellviewer;

public enum Action {
    STANDARDACTION,
    QUICKACTION,
    FREEACTION,
    FULLACTION;

    @Override
    public String toString() {
        switch (this) {
            case STANDARDACTION:
                return MainActivity.resources.getString(R.string.standardAction);
            case QUICKACTION:
                return MainActivity.resources.getString(R.string.quickAction);
            case FREEACTION:
                return MainActivity.resources.getString(R.string.freeAction);
            case FULLACTION:
                return MainActivity.resources.getString(R.string.fullAction);
            default:
                return null;
        }
    }
}
