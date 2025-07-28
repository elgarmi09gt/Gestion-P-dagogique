package saisie_notes_mode_ens_inter;

import java.util.Map;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import oracle.adf.share.logging.ADFLogger;

import org.apache.commons.lang3.StringUtils;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.style.Selector;
import org.apache.myfaces.trinidad.style.Style;
import org.apache.myfaces.trinidad.style.Styles;

public class StylesContext
{
  private static ADFLogger logger = ADFLogger.createADFLogger(StylesContext.class);

  static StylesContext currentInstance;
  Styles styles;

  private StylesContext()
  {
  }

  public static StylesContext getCurrentInstance()
  {
    if (currentInstance == null)
      currentInstance = new StylesContext();
    return currentInstance;
  }

  public void catchStyleMap(PhaseEvent phaseEvent)
  {
    RenderingContext rCtx = RenderingContext.getCurrentInstance();
    if (styles == null && phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE && rCtx != null)
      styles = rCtx.getStyles();
  }

  public String getCssFromStyleClass(String styleClass)
  {
    if (styles == null)
    {
      logger.severe("RenderingContext styles not found!");
      return "";
    }
    Map<Selector, Style> ssMap = styles.getSelectorStyleMap();
    if (StringUtils.isBlank(styleClass))
      return "";
    StringBuilder css = new StringBuilder();
    String[] classes = styleClass.split("\\s+");
    for (String clazz : classes)
    {
      Selector selector = Selector.createSelector("." + clazz);
      if (ssMap.containsKey(selector))
      {
        Style style = ssMap.get(selector);
        if (StringUtils.isNotBlank(style.toInlineString()))
        {
          css.append(style.toInlineString());
          css.append(";");
        }
      }
    }
    return css.toString();
  }
}
