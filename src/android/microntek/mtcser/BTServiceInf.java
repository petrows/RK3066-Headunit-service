package android.microntek.mtcser;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract interface BTServiceInf
  extends IInterface
{
  public abstract void answerCall()
    throws RemoteException;
  
  public abstract void avPlayNext()
    throws RemoteException;
  
  public abstract void avPlayPause()
    throws RemoteException;
  
  public abstract void avPlayPrev()
    throws RemoteException;
  
  public abstract void avPlayStop()
    throws RemoteException;
  
  public abstract void connectBT(String paramString)
    throws RemoteException;
  
  public abstract void connectOBD(String paramString)
    throws RemoteException;
  
  public abstract void deleteBT(String paramString)
    throws RemoteException;
  
  public abstract void deleteHistory(int paramInt)
    throws RemoteException;
  
  public abstract void deleteHistoryAll()
    throws RemoteException;
  
  public abstract void deleteOBD(String paramString)
    throws RemoteException;
  
  public abstract void dialOut(String paramString)
    throws RemoteException;
  
  public abstract void dialOutSub(char paramChar)
    throws RemoteException;
  
  public abstract void disconnectBT(String paramString)
    throws RemoteException;
  
  public abstract void disconnectOBD(String paramString)
    throws RemoteException;
  
  public abstract byte getAVState()
    throws RemoteException;
  
  public abstract boolean getAutoAnswer()
    throws RemoteException;
  
  public abstract boolean getAutoConnect()
    throws RemoteException;
  
  public abstract byte getBTState()
    throws RemoteException;
  
  public abstract String getCallInNum()
    throws RemoteException;
  
  public abstract List<String> getDeviceList()
    throws RemoteException;
  
  public abstract String getDialOutNum()
    throws RemoteException;
  
  public abstract List<String> getHistoryList()
    throws RemoteException;
  
  public abstract List<String> getMatchList()
    throws RemoteException;
  
  public abstract String getModuleName()
    throws RemoteException;
  
  public abstract String getModulePassword()
    throws RemoteException;
  
  public abstract long getNowDevAddr()
    throws RemoteException;
  
  public abstract String getNowDevName()
    throws RemoteException;
  
  public abstract String getPhoneNum()
    throws RemoteException;
  
  public abstract void hangupCall()
    throws RemoteException;
  
  public abstract void init()
    throws RemoteException;
  
  public abstract void musicMute()
    throws RemoteException;
  
  public abstract void musicUnmute()
    throws RemoteException;
  
  public abstract void rejectCall()
    throws RemoteException;
  
  public abstract void scanStart()
    throws RemoteException;
  
  public abstract void scanStop()
    throws RemoteException;
  
  public abstract void setAutoAnswer(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setAutoConnect(boolean paramBoolean)
    throws RemoteException;
  
  public abstract void setModuleName(String paramString)
    throws RemoteException;
  
  public abstract void setModulePassword(String paramString)
    throws RemoteException;
  
  public abstract void switchVoice()
    throws RemoteException;
  
  public abstract void syncMatchList()
    throws RemoteException;
  
  public abstract void syncPhonebook()
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements BTServiceInf
  {
    private static final String DESCRIPTOR = "android.microntek.mtcser.BTServiceInf";
    static final int TRANSACTION_answerCall = 13;
    static final int TRANSACTION_avPlayNext = 12;
    static final int TRANSACTION_avPlayPause = 9;
    static final int TRANSACTION_avPlayPrev = 11;
    static final int TRANSACTION_avPlayStop = 10;
    static final int TRANSACTION_connectBT = 26;
    static final int TRANSACTION_connectOBD = 28;
    static final int TRANSACTION_deleteBT = 31;
    static final int TRANSACTION_deleteHistory = 36;
    static final int TRANSACTION_deleteHistoryAll = 37;
    static final int TRANSACTION_deleteOBD = 30;
    static final int TRANSACTION_dialOut = 42;
    static final int TRANSACTION_dialOutSub = 43;
    static final int TRANSACTION_disconnectBT = 27;
    static final int TRANSACTION_disconnectOBD = 29;
    static final int TRANSACTION_getAVState = 3;
    static final int TRANSACTION_getAutoAnswer = 25;
    static final int TRANSACTION_getAutoConnect = 23;
    static final int TRANSACTION_getBTState = 2;
    static final int TRANSACTION_getCallInNum = 5;
    static final int TRANSACTION_getDeviceList = 34;
    static final int TRANSACTION_getDialOutNum = 4;
    static final int TRANSACTION_getHistoryList = 35;
    static final int TRANSACTION_getMatchList = 33;
    static final int TRANSACTION_getModuleName = 18;
    static final int TRANSACTION_getModulePassword = 19;
    static final int TRANSACTION_getNowDevAddr = 7;
    static final int TRANSACTION_getNowDevName = 8;
    static final int TRANSACTION_getPhoneNum = 6;
    static final int TRANSACTION_hangupCall = 14;
    static final int TRANSACTION_init = 1;
    static final int TRANSACTION_musicMute = 38;
    static final int TRANSACTION_musicUnmute = 39;
    static final int TRANSACTION_rejectCall = 15;
    static final int TRANSACTION_scanStart = 40;
    static final int TRANSACTION_scanStop = 41;
    static final int TRANSACTION_setAutoAnswer = 24;
    static final int TRANSACTION_setAutoConnect = 22;
    static final int TRANSACTION_setModuleName = 20;
    static final int TRANSACTION_setModulePassword = 21;
    static final int TRANSACTION_switchVoice = 16;
    static final int TRANSACTION_syncMatchList = 32;
    static final int TRANSACTION_syncPhonebook = 17;
    
    public Stub()
    {
      attachInterface(this, "android.microntek.mtcser.BTServiceInf");
    }
    
    public static BTServiceInf asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("android.microntek.mtcser.BTServiceInf");
      if ((localIInterface != null) && ((localIInterface instanceof BTServiceInf))) {
        return (BTServiceInf)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      case 43: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        dialOutSub((char)paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 42: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        dialOut(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 41: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        scanStop();
        paramParcel2.writeNoException();
        return true;
      case 40: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        scanStart();
        paramParcel2.writeNoException();
        return true;
      case 39: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        musicUnmute();
        paramParcel2.writeNoException();
        return true;
      case 38: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        musicMute();
        paramParcel2.writeNoException();
        return true;
      case 37: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        deleteHistoryAll();
        paramParcel2.writeNoException();
        return true;
      case 36: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        deleteHistory(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 35: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        List<String> localList3 = getHistoryList();
        paramParcel2.writeNoException();
        paramParcel2.writeStringList(localList3);
        return true;
      case 34: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        List<String> localList2 = getDeviceList();
        paramParcel2.writeNoException();
        paramParcel2.writeStringList(localList2);
        return true;
      case 33: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        List<String> localList1 = getMatchList();
        paramParcel2.writeNoException();
        paramParcel2.writeStringList(localList1);
        return true;
      case 32: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        syncMatchList();
        paramParcel2.writeNoException();
        return true;
      case 31: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        deleteBT(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 30: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        deleteOBD(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 29: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        disconnectOBD(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 28: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        connectOBD(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 27: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        disconnectBT(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 26: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        connectBT(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 25: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        boolean bool4 = getAutoAnswer();
        paramParcel2.writeNoException();
        int m = 0;
        if (bool4) {
          m = 1;
        }
        paramParcel2.writeInt(m);
        return true;
      case 24: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        int k = paramParcel1.readInt();
        boolean bool3 = false;
        if (k != 0) {
          bool3 = true;
        }
        setAutoAnswer(bool3);
        paramParcel2.writeNoException();
        return true;
      case 23: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        boolean bool2 = getAutoConnect();
        paramParcel2.writeNoException();
        int j = 0;
        if (bool2) {
          j = 1;
        }
        paramParcel2.writeInt(j);
        return true;
      case 22: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        int i = paramParcel1.readInt();
        boolean bool1 = false;
        if (i != 0) {
          bool1 = true;
        }
        setAutoConnect(bool1);
        paramParcel2.writeNoException();
        return true;
      case 21: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        setModulePassword(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 20: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        setModuleName(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 19: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        String str6 = getModulePassword();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str6);
        return true;
      case 18: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        String str5 = getModuleName();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str5);
        return true;
      case 17: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        syncPhonebook();
        paramParcel2.writeNoException();
        return true;
      case 16: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        switchVoice();
        paramParcel2.writeNoException();
        return true;
      case 15: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        rejectCall();
        paramParcel2.writeNoException();
        return true;
      case 14: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        hangupCall();
        paramParcel2.writeNoException();
        return true;
      case 13: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        answerCall();
        paramParcel2.writeNoException();
        return true;
      case 12: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        avPlayNext();
        paramParcel2.writeNoException();
        return true;
      case 11: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        avPlayPrev();
        paramParcel2.writeNoException();
        return true;
      case 10: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        avPlayStop();
        paramParcel2.writeNoException();
        return true;
      case 9: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        avPlayPause();
        paramParcel2.writeNoException();
        return true;
      case 8: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        String str4 = getNowDevName();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str4);
        return true;
      case 7: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        long l = getNowDevAddr();
        paramParcel2.writeNoException();
        paramParcel2.writeLong(l);
        return true;
      case 6: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        String str3 = getPhoneNum();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str3);
        return true;
      case 5: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        String str2 = getCallInNum();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str2);
        return true;
      case 4: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        String str1 = getDialOutNum();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str1);
        return true;
      case 3: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        byte b2 = getAVState();
        paramParcel2.writeNoException();
        paramParcel2.writeByte(b2);
        return true;
      case 2: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        byte b1 = getBTState();
        paramParcel2.writeNoException();
        paramParcel2.writeByte(b1);
        return true;
      case 1: 
        paramParcel1.enforceInterface("android.microntek.mtcser.BTServiceInf");
        init();
        paramParcel2.writeNoException();
        return true;
      case 1598968902: 
        paramParcel2.writeString("android.microntek.mtcser.BTServiceInf");
        return true;
      }
      return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
    }
    
    private static class Proxy
      implements BTServiceInf
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public void answerCall()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public void avPlayNext()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void avPlayPause()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void avPlayPrev()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void avPlayStop()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void connectBT(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void connectOBD(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void deleteBT(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(31, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void deleteHistory(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(36, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void deleteHistoryAll()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(37, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void deleteOBD(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(30, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void dialOut(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(42, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void dialOutSub(char paramChar)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeInt(paramChar);
          this.mRemote.transact(43, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void disconnectBT(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void disconnectOBD(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public byte getAVState()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          byte b = localParcel2.readByte();
          return b;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean getAutoAnswer()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(25, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean getAutoConnect()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public byte getBTState()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          byte b = localParcel2.readByte();
          return b;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getCallInNum()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public List<String> getDeviceList()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(34, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList<String> localArrayList = localParcel2.createStringArrayList();
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getDialOutNum()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public List<String> getHistoryList()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(35, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList<String> localArrayList = localParcel2.createStringArrayList();
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "android.microntek.mtcser.BTServiceInf";
      }
      
      public List<String> getMatchList()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(33, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList<String> localArrayList = localParcel2.createStringArrayList();
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getModuleName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getModulePassword()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public long getNowDevAddr()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          long l = localParcel2.readLong();
          return l;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getNowDevName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getPhoneNum()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void hangupCall()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(14, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void init()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void musicMute()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(38, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void musicUnmute()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(39, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void rejectCall()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(15, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void scanStart()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(40, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void scanStop()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(41, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setAutoAnswer(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          int i = 0;
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(24, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setAutoConnect(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          int i = 0;
          if (paramBoolean) {
            i = 1;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setModuleName(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(20, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setModulePassword(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          localParcel1.writeString(paramString);
          this.mRemote.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void switchVoice()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void syncMatchList()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(32, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void syncPhonebook()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.microntek.mtcser.BTServiceInf");
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}


/* Location:           H:\devel\projects\car-android-klyde\fw-unpack\fw-kld-2.03\system\app\MTCBlueTooth3\classes.dex.jar
 * Qualified Name:     android.microntek.mtcser.BTServiceInf
 * JD-Core Version:    0.7.1
 */