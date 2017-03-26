package net.Red.atm.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import net.Red.atm.Account;

public class AccountPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new AccountPO(new Account[]{});
      } else {
          return new AccountPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return net.Red.atm.util.CreatorCreator.createIdMap(sessionID);
   }
}
