package blood.steel.server.model.util.messaging;

import java.util.List;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.fight.messaging.ControlMessage;
import blood.steel.server.model.fight.messaging.CurrentEnemyMessage;
import blood.steel.server.model.fight.messaging.FightMessage;
import blood.steel.server.model.fight.messaging.FightMessageList;
import blood.steel.server.model.messaging.*;

public class FightMessageFactory {

	private static FightMessageFactory factory = new FightMessageFactory();
	
	private FightMessageFactory() {  }
	
	public static FightMessageFactory getFightMessageFactory() {
		return factory;
	}
	
	public GameMessage createFightNoAttackMessage(CharacterHandler character) {
		InformationMessage message = new InformationMessage("You cannot attack " + character.getName());
		message.setCode(1006);
		return message;
	}
	public GameMessage createFightNoFinishMessage() {
		InformationMessage message = new InformationMessage("You cannot finish your enemy!");
		message.setCode(1007);
		return message;
	}
	
	public GameMessage createFightControlAttackableMessage(CharacterHandler character) {
		ControlMessage message = new ControlMessage();
		message.setCommand("attackable");
		message.setDetail(character.getName());
		return message;
	}
	public GameMessage createFightControlNotAttackableMessage(CharacterHandler character) {
		ControlMessage message = new ControlMessage();
		message.setCommand("not_attackable");
		message.setDetail(character.getName());
		return message;
	}
	public GameMessage createFightControlFinishMessage(CharacterHandler character) {
		ControlMessage message = new ControlMessage();
		message.setCommand("finish");
		message.setDetail(character.getName());
		return message;
	}
	public GameMessage createFightControlNotFinishMessage(CharacterHandler character) {
		ControlMessage message = new ControlMessage();
		message.setCommand("non_finish");
		message.setDetail(character.getName());
		return message;
	}
	public GameMessage createFightControlEnemyMessage(CharacterHandler character) {
		CurrentEnemyMessage message = new CurrentEnemyMessage(character);
		message.setCode(1005);		
		return message;
	}
	public GameMessage createFightMessageList(List<FightMessage> messages) {
		FightMessageList list = new FightMessageList();
		list.setMessage(messages);
		list.setPageNumber(messages.get(0).getPage());
		return list;
	}
	
}
