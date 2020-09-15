package p4.events;

import p4.UI.ListMails;

/**
 * Event fired when a user deletes a mail in listMails
 */
public class DeleteMailsEvent {
    /**
     * Mails to delete
     */
    public ListMails mails;

    public DeleteMailsEvent(ListMails mails) {
        this.mails = mails;
    }
}
