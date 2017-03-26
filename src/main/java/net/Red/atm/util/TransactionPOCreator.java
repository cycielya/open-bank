package net.Red.atm.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import net.Red.atm.Transaction;

public class TransactionPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new TransactionPO(new Transaction[]{});
      } else {
          return new TransactionPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return net.Red.atm.util.CreatorCreator.createIdMap(sessionID);
   }
}
