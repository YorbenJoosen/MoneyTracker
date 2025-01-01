package app.ticket;

public enum TicketType {
    airplane,
    restaurant,
    ;

    @Override
    public String toString() {
        return switch (this) {
            case airplane -> "airplane";
            case restaurant -> "restaurant";
            default -> "";
        };
    }
}

