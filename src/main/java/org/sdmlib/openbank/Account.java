/*
   Copyright (c) 2017 FA
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package org.sdmlib.openbank;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import de.uniks.networkparser.EntityUtil;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.Transaction;
   /**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class Account implements SendableEntity
{

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = null;
   
   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null) {
   		listeners.firePropertyChange(propertyName, oldValue, newValue);
   		return true;
   	}
   	return false;
   }
   
   public boolean addPropertyChangeListener(PropertyChangeListener listener) 
   {
   	if (listeners == null) {
   		listeners = new PropertyChangeSupport(this);
   	}
   	listeners.addPropertyChangeListener(listener);
   	return true;
   }
   
   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
   	if (listeners == null) {
   		listeners = new PropertyChangeSupport(this);
   	}
   	listeners.addPropertyChangeListener(propertyName, listener);
   	return true;
   }
   
   public boolean removePropertyChangeListener(PropertyChangeListener listener) {
   	if (listeners == null) {
   		listeners.removePropertyChangeListener(listener);
   	}
   	listeners.removePropertyChangeListener(listener);
   	return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
   	if (listeners != null) {
   		listeners.removePropertyChangeListener(propertyName, listener);
   	}
   	return true;
   }

   
   //==========================================================================
   
   
   public void removeYou()
   {
      setOwner(null);
      withoutTransaction(this.getTransaction().toArray(new Transaction[this.getTransaction().size()]));
      withoutToAccount(this.getToAccount().toArray(new Transaction[this.getToAccount().size()]));
      withoutFromAccount(this.getFromAccount().toArray(new Transaction[this.getFromAccount().size()]));
      withoutCredit(this.getCredit().toArray(new Transaction[this.getCredit().size()]));
      withoutDebit(this.getDebit().toArray(new Transaction[this.getDebit().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_BALANCE = "balance";
   
   private double balance;

   public double getBalance()
   {
      return this.balance;
   }
   
   public void setBalance(double value)
   {
      if (this.balance != value) {
      
         double oldValue = this.balance;
         this.balance = value;
         this.firePropertyChange(PROPERTY_BALANCE, oldValue, value);
      }
   }
   
   public Account withBalance(double value)
   {
      setBalance(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getBalance());
      result.append(" ").append(this.getAccountnum());
      result.append(" ").append(this.getCreationdate());
      return result.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_ACCOUNTNUM = "accountnum";
   
   private int accountnum;

   public int getAccountnum()
   {
      return this.accountnum;
   }
   
   public void setAccountnum(int value)
   {
      if (this.accountnum != value) {
      
         int oldValue = this.accountnum;
         this.accountnum = value;
         this.firePropertyChange(PROPERTY_ACCOUNTNUM, oldValue, value);
      }
   }
   
   public Account withAccountnum(int value)
   {
      setAccountnum(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_CREATIONDATE = "creationdate";
   
   private String creationdate;

   public String getCreationdate()
   {
      return this.creationdate;
   }
   
   public void setCreationdate(String value)
   {
      if ( ! EntityUtil.stringEquals(this.creationdate, value)) {
      
         String oldValue = this.creationdate;
         this.creationdate = value;
         this.firePropertyChange(PROPERTY_CREATIONDATE, oldValue, value);
      }
   }
   
   public Account withCreationdate(String value)
   {
      setCreationdate(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Account ----------------------------------- User
    *              account                   owner
    * </pre>
    */
   
   public static final String PROPERTY_OWNER = "owner";

   private User owner = null;

   public User getOwner()
   {
      return this.owner;
   }

   public boolean setOwner(User value)
   {
      boolean changed = false;
      
      if (this.owner != value)
      {
         User oldValue = this.owner;
         
         if (this.owner != null)
         {
            this.owner = null;
            oldValue.withoutAccount(this);
         }
         
         this.owner = value;
         
         if (value != null)
         {
            value.withAccount(this);
         }
         
         firePropertyChange(PROPERTY_OWNER, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Account withOwner(User value)
   {
      setOwner(value);
      return this;
   } 

   public User createOwner()
   {
      User value = new User();
      withOwner(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              toAccount                   transaction
    * </pre>
    */
   
   public static final String PROPERTY_TRANSACTION = "transaction";

   private TransactionSet transaction = null;
   
   public TransactionSet getTransaction()
   {
      if (this.transaction == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.transaction;
   }

   public Account withTransaction(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.transaction == null)
            {
               this.transaction = new TransactionSet();
            }
            
            boolean changed = this.transaction.add (item);

            if (changed)
            {
               item.withToAccount(this);
               firePropertyChange(PROPERTY_TRANSACTION, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutTransaction(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.transaction != null) && (item != null))
         {
            if (this.transaction.remove(item))
            {
               item.setToAccount(null);
               firePropertyChange(PROPERTY_TRANSACTION, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createTransaction()
   {
      Transaction value = new Transaction();
      withTransaction(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              transaction                   toAccount
    * </pre>
    */
   
   public static final String PROPERTY_TOACCOUNT = "toAccount";

   private TransactionSet toAccount = null;
   
   public TransactionSet getToAccount()
   {
      if (this.toAccount == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.toAccount;
   }

   public Account withToAccount(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.toAccount == null)
            {
               this.toAccount = new TransactionSet();
            }
            
            boolean changed = this.toAccount.add (item);

            if (changed)
            {
               item.withTransaction(this);
               firePropertyChange(PROPERTY_TOACCOUNT, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutToAccount(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.toAccount != null) && (item != null))
         {
            if (this.toAccount.remove(item))
            {
               item.setTransaction(null);
               firePropertyChange(PROPERTY_TOACCOUNT, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createToAccount()
   {
      Transaction value = new Transaction();
      withToAccount(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              transaction                   fromAccount
    * </pre>
    */
   
   public static final String PROPERTY_FROMACCOUNT = "fromAccount";

   private TransactionSet fromAccount = null;
   
   public TransactionSet getFromAccount()
   {
      if (this.fromAccount == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.fromAccount;
   }

   public Account withFromAccount(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.fromAccount == null)
            {
               this.fromAccount = new TransactionSet();
            }
            
            boolean changed = this.fromAccount.add (item);

            if (changed)
            {
               item.withTransaction(this);
               firePropertyChange(PROPERTY_FROMACCOUNT, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutFromAccount(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.fromAccount != null) && (item != null))
         {
            if (this.fromAccount.remove(item))
            {
               item.setTransaction(null);
               firePropertyChange(PROPERTY_FROMACCOUNT, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createFromAccount()
   {
      Transaction value = new Transaction();
      withFromAccount(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              toAccount                   credit
    * </pre>
    */
   
   public static final String PROPERTY_CREDIT = "credit";

   private TransactionSet credit = null;
   
   public TransactionSet getCredit()
   {
      if (this.credit == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.credit;
   }

   public Account withCredit(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.credit == null)
            {
               this.credit = new TransactionSet();
            }
            
            boolean changed = this.credit.add (item);

            if (changed)
            {
               item.withToAccount(this);
               firePropertyChange(PROPERTY_CREDIT, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutCredit(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.credit != null) && (item != null))
         {
            if (this.credit.remove(item))
            {
               item.setToAccount(null);
               firePropertyChange(PROPERTY_CREDIT, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createCredit()
   {
      Transaction value = new Transaction();
      withCredit(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              fromAccount                   debit
    * </pre>
    */
   
   public static final String PROPERTY_DEBIT = "debit";

   private TransactionSet debit = null;
   
   public TransactionSet getDebit()
   {
      if (this.debit == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.debit;
   }

   public Account withDebit(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.debit == null)
            {
               this.debit = new TransactionSet();
            }
            
            boolean changed = this.debit.add (item);

            if (changed)
            {
               item.withFromAccount(this);
               firePropertyChange(PROPERTY_DEBIT, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutDebit(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.debit != null) && (item != null))
         {
            if (this.debit.remove(item))
            {
               item.setFromAccount(null);
               firePropertyChange(PROPERTY_DEBIT, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createDebit()
   {
      Transaction value = new Transaction();
      withDebit(value);
      return value;
   } 
}
