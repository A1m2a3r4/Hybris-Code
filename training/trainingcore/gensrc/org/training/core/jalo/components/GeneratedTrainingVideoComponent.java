/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Oct 24, 2024, 3:13:46 PM                    ---
 * ----------------------------------------------------------------
 */
package org.training.core.jalo.components;

import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.training.core.constants.TrainingCoreConstants;

/**
 * Generated class for type {@link org.training.core.jalo.components.TrainingVideoComponent TrainingVideoComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedTrainingVideoComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>TrainingVideoComponent.width</code> attribute **/
	public static final String WIDTH = "width";
	/** Qualifier of the <code>TrainingVideoComponent.height</code> attribute **/
	public static final String HEIGHT = "height";
	/** Qualifier of the <code>TrainingVideoComponent.videoId</code> attribute **/
	public static final String VIDEOID = "videoId";
	/** Qualifier of the <code>TrainingVideoComponent.autoPlay</code> attribute **/
	public static final String AUTOPLAY = "autoPlay";
	/** Qualifier of the <code>TrainingVideoComponent.showControls</code> attribute **/
	public static final String SHOWCONTROLS = "showControls";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(WIDTH, AttributeMode.INITIAL);
		tmp.put(HEIGHT, AttributeMode.INITIAL);
		tmp.put(VIDEOID, AttributeMode.INITIAL);
		tmp.put(AUTOPLAY, AttributeMode.INITIAL);
		tmp.put(SHOWCONTROLS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.autoPlay</code> attribute.
	 * @return the autoPlay
	 */
	public Boolean isAutoPlay(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, AUTOPLAY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.autoPlay</code> attribute.
	 * @return the autoPlay
	 */
	public Boolean isAutoPlay()
	{
		return isAutoPlay( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.autoPlay</code> attribute. 
	 * @return the autoPlay
	 */
	public boolean isAutoPlayAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isAutoPlay( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.autoPlay</code> attribute. 
	 * @return the autoPlay
	 */
	public boolean isAutoPlayAsPrimitive()
	{
		return isAutoPlayAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, AUTOPLAY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final Boolean value)
	{
		setAutoPlay( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final SessionContext ctx, final boolean value)
	{
		setAutoPlay( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final boolean value)
	{
		setAutoPlay( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.height</code> attribute.
	 * @return the height
	 */
	public Integer getHeight(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, HEIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.height</code> attribute.
	 * @return the height
	 */
	public Integer getHeight()
	{
		return getHeight( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.height</code> attribute. 
	 * @return the height
	 */
	public int getHeightAsPrimitive(final SessionContext ctx)
	{
		Integer value = getHeight( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.height</code> attribute. 
	 * @return the height
	 */
	public int getHeightAsPrimitive()
	{
		return getHeightAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.height</code> attribute. 
	 * @param value the height
	 */
	public void setHeight(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, HEIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.height</code> attribute. 
	 * @param value the height
	 */
	public void setHeight(final Integer value)
	{
		setHeight( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.height</code> attribute. 
	 * @param value the height
	 */
	public void setHeight(final SessionContext ctx, final int value)
	{
		setHeight( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.height</code> attribute. 
	 * @param value the height
	 */
	public void setHeight(final int value)
	{
		setHeight( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.showControls</code> attribute.
	 * @return the showControls
	 */
	public Boolean isShowControls(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOWCONTROLS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.showControls</code> attribute.
	 * @return the showControls
	 */
	public Boolean isShowControls()
	{
		return isShowControls( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.showControls</code> attribute. 
	 * @return the showControls
	 */
	public boolean isShowControlsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShowControls( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.showControls</code> attribute. 
	 * @return the showControls
	 */
	public boolean isShowControlsAsPrimitive()
	{
		return isShowControlsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.showControls</code> attribute. 
	 * @param value the showControls
	 */
	public void setShowControls(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOWCONTROLS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.showControls</code> attribute. 
	 * @param value the showControls
	 */
	public void setShowControls(final Boolean value)
	{
		setShowControls( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.showControls</code> attribute. 
	 * @param value the showControls
	 */
	public void setShowControls(final SessionContext ctx, final boolean value)
	{
		setShowControls( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.showControls</code> attribute. 
	 * @param value the showControls
	 */
	public void setShowControls(final boolean value)
	{
		setShowControls( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.videoId</code> attribute.
	 * @return the videoId
	 */
	public String getVideoId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VIDEOID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.videoId</code> attribute.
	 * @return the videoId
	 */
	public String getVideoId()
	{
		return getVideoId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.videoId</code> attribute. 
	 * @param value the videoId
	 */
	public void setVideoId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VIDEOID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.videoId</code> attribute. 
	 * @param value the videoId
	 */
	public void setVideoId(final String value)
	{
		setVideoId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.width</code> attribute.
	 * @return the width
	 */
	public Integer getWidth(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, WIDTH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.width</code> attribute.
	 * @return the width
	 */
	public Integer getWidth()
	{
		return getWidth( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.width</code> attribute. 
	 * @return the width
	 */
	public int getWidthAsPrimitive(final SessionContext ctx)
	{
		Integer value = getWidth( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>TrainingVideoComponent.width</code> attribute. 
	 * @return the width
	 */
	public int getWidthAsPrimitive()
	{
		return getWidthAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.width</code> attribute. 
	 * @param value the width
	 */
	public void setWidth(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, WIDTH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.width</code> attribute. 
	 * @param value the width
	 */
	public void setWidth(final Integer value)
	{
		setWidth( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.width</code> attribute. 
	 * @param value the width
	 */
	public void setWidth(final SessionContext ctx, final int value)
	{
		setWidth( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>TrainingVideoComponent.width</code> attribute. 
	 * @param value the width
	 */
	public void setWidth(final int value)
	{
		setWidth( getSession().getSessionContext(), value );
	}
	
}
