package main.app.ticket;

public enum TicketType {
    airplane,
    restaurant,
    ;

    @Override
    public String toString() {
        switch (this) {
            case airplane:
                return "airplane";
            case restaurant:
                return "restaurant";
            default: return "";
        }
    }
}

