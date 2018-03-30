package BWJSAL.event.listener;

/**
 * The listener interface for receiving bot.notification of game onSendText events.
 * Any classes that are interested in monitoring onSendText events should implement this interface.
 */
public interface OnSendTextListener extends GameEventListener {
    /**
     * Called when the user attempts to send a text message.
     * This function can be used to make the bot execute text commands entered by the user for debugging purposes.
     * Note: If Flag::UserInput is disabled, then this function is not called.
     * @param text A string containing the exact text message that was sent by the user.
     */
    void onSendText(String text);
}
