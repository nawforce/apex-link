/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.platform.Component;

import com.nawforce.platform.ApexPages.Component;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Apex {
    public static class ActionFunction extends Component {}
    public static class ActionPoller extends Component {}
    public static class ActionRegion extends Component {}
    public static class ActionStatus extends Component {}
    public static class ActionSupport extends Component {}
    public static class AreaSeries extends Component {}
    public static class Axis extends Component {}
    public static class BarSeries extends Component {}
    public static class CanvasApp extends Component {}
    public static class Chart extends Component {}
    public static class ChartLabel extends Component {}
    public static class ChartTips extends Component {}
    public static class Column extends Component {}
    public static class CommandButton extends Component {}
    public static class CommandLink extends Component {}
    public static class DataList extends Component {}
    public static class DataTable extends Component {}
    public static class Detail extends Component {}
    public static class EmailPublisher extends Component {}
    public static class EnhancedList extends Component {}
    public static class Facet extends Component {}
    public static class Flash extends Component {}
    public static class Form extends Component {}
    public static class GaugeSeries extends Component {}
    public static class Iframe extends Component {}
    public static class Image extends Component {}
    public static class IncludeLightning extends Component {}
    public static class IncludeScript extends Component {}
    public static class InlineEditSupport extends Component {}
    public static class Input extends Component {}
    public static class InputCheckbox extends Component {}
    public static class InputField extends Component {}
    public static class InputFile extends Component {}
    public static class InputHidden extends Component {}
    public static class InputSecret extends Component {}
    public static class InputText extends Component {}
    public static class InputTextarea extends Component {}
    public static class Legend extends Component {}
    public static class LineSeries extends Component {}
    public static class ListViews extends Component {}
    public static class LogCallPublisher extends Component {}
    public static class Map extends Component {}
    public static class MapInfoWindow extends Component {}
    public static class MapMarker extends Component {}
    public static class Message extends Component {}
    public static class Messages extends Component {}
    public static class MilestoneTracker extends Component {}
    public static class OutputField extends Component {}

    public static class OutputLabel extends Component {
        public String accessKey;
        public String dir;
        public Boolean escape;
        public String for$;
        public String id;
        public String lang;
        public String onblur;
        public String onclick;
        public String ondblclick;
        public String onfocus;
        public String onkeydown;
        public String onkeypress;
        public String onkeyup;
        public String onmousedown;
        public String onmousemove;
        public String onmouseout;
        public String onmouseover;
        public String onmouseup;
        public Boolean rendered;
        public String style;
        public String styleClass;
        public String tabIndex;
        public String title;
        public String value;
    }

    public static class OutputLink extends Component {
        public String accessKey;
        public String charset;
        public String coords;
        public String dir;
        public Boolean disabled;
        public String hreflang;
        public String id;
        public String lang;
        public String onblur;
        public String onclick;
        public String ondblclick;
        public String onfocus;
        public String onkeydown;
        public String onkeypress;
        public String onkeyup;
        public String onmousedown;
        public String onmousemove;
        public String onmouseout;
        public String onmouseover;
        public String onmouseup;
        public String rel;
        public Boolean rendered;
        public String rev;
        public String shape;
        public String style;
        public String styleClass;
        public String tabIndex;
        public String target;
        public String title;
        public String type;
        public String value;
    }

    public static class OutputPanel extends Component {
        public String dir;
        public String id;
        public String lang;
        public String layout;
        public String onclick;
        public String ondblclick;
        public String onkeydown;
        public String onkeypress;
        public String onkeyup;
        public String onmousedown;
        public String onmousemove;
        public String onmouseout;
        public String onmouseover;
        public String onmouseup;
        public Boolean rendered;
        public String style;
        public String styleClass;
        public String title;
        public String value;
    }

    public static class OutputText extends Component {}
    public static class Page extends Component {}
    public static class PageBlock extends Component {}
    public static class PageBlockButtons extends Component {}
    public static class PageBlockSection extends Component {}
    public static class PageBlockSectionItem extends Component {}
    public static class PageBlockTable extends Component {}
    public static class PageMessage extends Component {}
    public static class PageMessages extends Component {}
    public static class PanelBar extends Component {}
    public static class PanelBarItem extends Component {}
    public static class PanelGrid extends Component {}
    public static class PanelGroup extends Component {}
    public static class PieSeries extends Component {}
    public static class RadarSeries extends Component {}
    public static class RelatedList extends Component {}
    public static class RemoteObjectField extends Component {}
    public static class RemoteObjectModel extends Component {}
    public static class RemoteObjects extends Component {}
    public static class Repeat extends Component {}
    public static class ScatterSeries extends Component {}
    public static class Scontrol extends Component {}
    public static class SectionHeader extends Component {}
    public static class SelectCheckboxes extends Component {}
    public static class SelectList extends Component {}
    public static class SelectOption extends Component {}
    public static class SelectOptions extends Component {}
    public static class SelectRadio extends Component {}
    public static class Slds extends Component {}
    public static class Stylesheet extends Component {}
    public static class Tab extends Component {}
    public static class TabPanel extends Component {}
    public static class Toolbar extends Component {}
    public static class ToolbarGroup extends Component {}
    public static class Variable extends Component {}
    public static class Vote extends Component {}
}
