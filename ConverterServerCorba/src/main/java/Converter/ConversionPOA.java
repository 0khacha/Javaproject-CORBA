package Converter;


/**
* Converter/ConversionPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from converter.idl
* Saturday, December 23, 2023 8:12:58 PM WET
*/

public abstract class ConversionPOA extends org.omg.PortableServer.Servant
 implements Converter.ConversionOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("convertNumber", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Converter/Conversion/convertNumber
       {
         String number = in.read_string ();
         short sourceBase = in.read_short ();
         short targetBase = in.read_short ();
         String $result = null;
         $result = this.convertNumber (number, sourceBase, targetBase);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Converter/Conversion:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Conversion _this() 
  {
    return ConversionHelper.narrow(
    super._this_object());
  }

  public Conversion _this(org.omg.CORBA.ORB orb) 
  {
    return ConversionHelper.narrow(
    super._this_object(orb));
  }


} // class ConversionPOA