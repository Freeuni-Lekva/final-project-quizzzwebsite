package messaging;

public class Message {


    private final String sender;
    private final String receiver;
    private final String message;
    private final String time;
    private final boolean unseen;

    public  Message(String sender, String receiver, String message, String time, boolean unseen){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
        this.unseen = unseen;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean isUnseen() {
        return unseen;
    }

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message p = (Message) o;
            return  sender.equals(p.getSender()) && receiver.equals(p.getReceiver())
                    && message.equals(p.getMessage()) && time.equals(p.getTime()) && unseen==p.isUnseen();
        }

}
