package saisie_notes_mode_ens_inter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SafeReflection
{
  public static boolean hasMethod(Object o, String name, Class[] parameterTypes)
  {
    try
    {
      o.getClass().getMethod(name, parameterTypes);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  public static Method getMethod(Object o, String methodName, Class[] parameterTypes)
  {
    try
    {
      return o.getClass().getMethod(methodName, parameterTypes);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  public static Object invoke(Object o, String methodName, Class[] parameterTypes, Object[] parameters)
  {
    try
    {
      return o.getClass().getMethod(methodName, parameterTypes).invoke(o, parameters);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  public static Object invoke(Object o, String methodName, Object[] parameters)
  {
    try
    {
      Class[] parameterTypes = new Class[parameters.length];
      for (int i = 0; i < parameters.length; i++)
      {
        Object p = parameters[i];
        if (p != null)
          parameterTypes[i] = p.getClass();
      }
      return o.getClass().getMethod(methodName, parameterTypes).invoke(o, parameters);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  public static Object getStaticFieldValue(String className, String fieldName)
  {
    try
    {
      Class cls = Class.forName(className);
      Field field = cls.getField(fieldName);
      return field.get(null);
    }
    catch (Exception e)
    {
      return null;
    }
  }
}
