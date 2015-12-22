package com.labatlas.atlas.commands;

import com.labatlas.atlas.Client;
import com.labatlas.atlas.Command;
import com.labatlas.atlas.Message;
import com.labatlas.atlas.ProtocolException;

/**
 *
 * @author Hwaipy
 */
public class ConnectionCommand extends Command {

  public ConnectionCommand() {
    super("Connection");
  }

  @Override
  public void execute(Message message, Client client) {
    if (client.isInitialed()) {
      throw new ProtocolException("Command \"Connection\" should only be thge first Message.", message);
    } else {
      String name = message.get(Message.KEY_NAME, String.class);
      if (client.init(name)) {
        Message response = message.response();
        response.put(Message.KEY_CLIENT_ID, client.getId());
        client.write(response);
      } else {
        Message response = message.responseError();
        response.put(Message.KEY_CLIENT_ID, client.getId()).put(Message.KEY_ERROR_MESSAGE, "Client name duplicated.");
        client.write(response);
      }
    }
  }

  @Override
  protected void executeCommand(Message message, Client client) {
  }

}
