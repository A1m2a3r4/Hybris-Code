/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Oct 24, 2024, 3:13:46 PM                    ---
 * ----------------------------------------------------------------
 */
package org.training.core.jalo;

import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.processengine.jalo.BusinessProcess;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.training.core.constants.TrainingCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.processengine.jalo.BusinessProcess MyTrainingProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedMyTrainingProcess extends BusinessProcess
{
	/** Qualifier of the <code>MyTrainingProcess.attribute1</code> attribute **/
	public static final String ATTRIBUTE1 = "attribute1";
	/** Qualifier of the <code>MyTrainingProcess.attribute2</code> attribute **/
	public static final String ATTRIBUTE2 = "attribute2";
	/** Qualifier of the <code>MyTrainingProcess.attribute3</code> attribute **/
	public static final String ATTRIBUTE3 = "attribute3";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(BusinessProcess.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ATTRIBUTE1, AttributeMode.INITIAL);
		tmp.put(ATTRIBUTE2, AttributeMode.INITIAL);
		tmp.put(ATTRIBUTE3, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MyTrainingProcess.attribute1</code> attribute.
	 * @return the attribute1 - Attribute 1
	 */
	public String getAttribute1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATTRIBUTE1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MyTrainingProcess.attribute1</code> attribute.
	 * @return the attribute1 - Attribute 1
	 */
	public String getAttribute1()
	{
		return getAttribute1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MyTrainingProcess.attribute1</code> attribute. 
	 * @param value the attribute1 - Attribute 1
	 */
	public void setAttribute1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATTRIBUTE1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MyTrainingProcess.attribute1</code> attribute. 
	 * @param value the attribute1 - Attribute 1
	 */
	public void setAttribute1(final String value)
	{
		setAttribute1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MyTrainingProcess.attribute2</code> attribute.
	 * @return the attribute2 - Attribute 2
	 */
	public String getAttribute2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATTRIBUTE2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MyTrainingProcess.attribute2</code> attribute.
	 * @return the attribute2 - Attribute 2
	 */
	public String getAttribute2()
	{
		return getAttribute2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MyTrainingProcess.attribute2</code> attribute. 
	 * @param value the attribute2 - Attribute 2
	 */
	public void setAttribute2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATTRIBUTE2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MyTrainingProcess.attribute2</code> attribute. 
	 * @param value the attribute2 - Attribute 2
	 */
	public void setAttribute2(final String value)
	{
		setAttribute2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MyTrainingProcess.attribute3</code> attribute.
	 * @return the attribute3 - Attribute 3
	 */
	public String getAttribute3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ATTRIBUTE3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>MyTrainingProcess.attribute3</code> attribute.
	 * @return the attribute3 - Attribute 3
	 */
	public String getAttribute3()
	{
		return getAttribute3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MyTrainingProcess.attribute3</code> attribute. 
	 * @param value the attribute3 - Attribute 3
	 */
	public void setAttribute3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ATTRIBUTE3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>MyTrainingProcess.attribute3</code> attribute. 
	 * @param value the attribute3 - Attribute 3
	 */
	public void setAttribute3(final String value)
	{
		setAttribute3( getSession().getSessionContext(), value );
	}
	
}
