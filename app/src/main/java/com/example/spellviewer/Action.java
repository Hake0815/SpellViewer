package com.example.spellviewer;

public enum Action {
    STANDARDACTION,
    BONUSACTION,
    FREEACTION,
    FULLACTION;

    @Override
    public String toString() {
        switch (this) {
            case STANDARDACTION:
                return MainActivity.resources.getString(R.string.standardAction);
            case BONUSACTION:
                return MainActivity.resources.getString(R.string.bonusAction);
            case FREEACTION:
                return MainActivity.resources.getString(R.string.freeAction);
            case FULLACTION:
                return MainActivity.resources.getString(R.string.fullAction);
            default:
                return "";
        }
    }
}
