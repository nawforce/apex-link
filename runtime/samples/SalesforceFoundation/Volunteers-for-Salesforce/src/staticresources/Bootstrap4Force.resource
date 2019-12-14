/*!
* Bootstrap v2.0.2
* Adapted to support Visualforce on Force.com
*
* Copyright 2012 Twitter, Inc
* Licensed under the Apache License v2.0
* http://www.apache.org/licenses/LICENSE-2.0
*
* Designed and built with all the love in the world @twitter by @mdo and @fat.
*/
#force article,
#force aside,
#force details,
#force figcaption,
#force figure,
#force footer,
#force header,
#force hgroup,
#force nav,
#force section {
  display: block;
}
#force audio,
#force canvas,
#force video {
  display: inline-block;
  *display: inline;
  *zoom: 1;
}
#force audio:not([controls]) {
  display: none;
}
#force html {
  font-size: 100%;
  -webkit-text-size-adjust: 100%;
  -ms-text-size-adjust: 100%;
}
#force a:focus {
  outline: thin dotted #333;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
#force a:hover,
#force a:active {
  outline: 0;
}
#force sub,
#force sup {
  position: relative;
  font-size: 75%;
  line-height: 0;
  vertical-align: baseline;
}
#force sup {
  top: -0.5em;
}
#force sub {
  bottom: -0.25em;
}
#force img {
  height: auto;
  border: 0;
  -ms-interpolation-mode: bicubic;
  vertical-align: middle;
}
#force button,
#force input,
#force select,
#force textarea {
  margin: 0;
  font-size: 100%;
  vertical-align: middle;
}
#force button,
#force input {
  *overflow: visible;
  line-height: normal;
}
#force button::-moz-focus-inner,
#force input::-moz-focus-inner {
  padding: 0;
  border: 0;
}
#force button,
#force input[type="button"],
#force input[type="reset"],
#force input[type="submit"] {
  cursor: pointer;
  -webkit-appearance: button;
}
#force input[type="search"] {
  -webkit-appearance: textfield;
  -webkit-box-sizing: content-box;
  -moz-box-sizing: content-box;
  box-sizing: content-box;
}
#force input[type="search"]::-webkit-search-decoration,
#force input[type="search"]::-webkit-search-cancel-button {
  -webkit-appearance: none;
}
#force textarea {
  overflow: auto;
  vertical-align: top;
}
#force .clearfix {
  *zoom: 1;
}
#force .clearfix:before,
#force .clearfix:after {
  display: table;
  content: "";
}
#force .clearfix:after {
  clear: both;
}
#force .hide-text {
  overflow: hidden;
  text-indent: 100%;
  white-space: nowrap;
}
#force .input-block-level {
  display: block;
  width: 100%;
  min-height: 28px;
  /* Make inputs at least the height of their button counterpart */

  /* Makes inputs behave like true block-level elements */

  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  -ms-box-sizing: border-box;
  box-sizing: border-box;
}
#force body {
  margin: 0;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  line-height: 18px;
  color: #333333;
  background-color: #ffffff;
}
#force a {
  color: #0088cc;
  text-decoration: none;
}
#force a:hover {
  color: #005580;
  text-decoration: underline;
}
#force .row {
  margin-left: -20px;
  *zoom: 1;
}
#force .row:before,
#force .row:after {
  display: table;
  content: "";
}
#force .row:after {
  clear: both;
}
#force [class*="span"] {
  float: left;
  margin-left: 20px;
}
#force .container,
#force .navbar-fixed-top .container,
#force .navbar-fixed-bottom .container {
  width: 940px;
}
#force .span12 {
  width: 940px;
}
#force .span11 {
  width: 860px;
}
#force .span10 {
  width: 780px;
}
#force .span9 {
  width: 700px;
}
#force .span8 {
  width: 620px;
}
#force .span7 {
  width: 540px;
}
#force .span6 {
  width: 460px;
}
#force .span5 {
  width: 380px;
}
#force .span4 {
  width: 300px;
}
#force .span3 {
  width: 220px;
}
#force .span2 {
  width: 140px;
}
#force .span1 {
  width: 60px;
}
#force .offset12 {
  margin-left: 980px;
}
#force .offset11 {
  margin-left: 900px;
}
#force .offset10 {
  margin-left: 820px;
}
#force .offset9 {
  margin-left: 740px;
}
#force .offset8 {
  margin-left: 660px;
}
#force .offset7 {
  margin-left: 580px;
}
#force .offset6 {
  margin-left: 500px;
}
#force .offset5 {
  margin-left: 420px;
}
#force .offset4 {
  margin-left: 340px;
}
#force .offset3 {
  margin-left: 260px;
}
#force .offset2 {
  margin-left: 180px;
}
#force .offset1 {
  margin-left: 100px;
}
#force .row-fluid {
  width: 100%;
  *zoom: 1;
}
#force .row-fluid:before,
#force .row-fluid:after {
  display: table;
  content: "";
}
#force .row-fluid:after {
  clear: both;
}
#force .row-fluid > [class*="span"] {
  float: left;
  margin-left: 2.127659574%;
}
#force .row-fluid > [class*="span"]:first-child {
  margin-left: 0;
}
#force .row-fluid > .span12 {
  width: 99.99999998999999%;
}
#force .row-fluid > .span11 {
  width: 91.489361693%;
}
#force .row-fluid > .span10 {
  width: 82.97872339599999%;
}
#force .row-fluid > .span9 {
  width: 74.468085099%;
}
#force .row-fluid > .span8 {
  width: 65.95744680199999%;
}
#force .row-fluid > .span7 {
  width: 57.446808505%;
}
#force .row-fluid > .span6 {
  width: 48.93617020799999%;
}
#force .row-fluid > .span5 {
  width: 40.425531911%;
}
#force .row-fluid > .span4 {
  width: 31.914893614%;
}
#force .row-fluid > .span3 {
  width: 23.404255317%;
}
#force .row-fluid > .span2 {
  width: 14.89361702%;
}
#force .row-fluid > .span1 {
  width: 6.382978723%;
}
#force .container {
  *zoom: 1;
}
#force .container:before,
#force .container:after {
  display: table;
  content: "";
}
#force .container:after {
  clear: both;
}
#force .container-fluid {
  *zoom: 1;
}
#force .container-fluid:before,
#force .container-fluid:after {
  display: table;
  content: "";
}
#force .container-fluid:after {
  clear: both;
}
#force p {
  margin: 0 0 9px;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  line-height: 18px;
}
#force p small {
  font-size: 11px;
  color: #999999;
}
#force .lead {
  margin-bottom: 18px;
  font-size: 20px;
  font-weight: 200;
  line-height: 27px;
}
#force h1,
#force h2,
#force h3,
#force h4,
#force h5,
#force h6 {
  margin: 0;
  font-family: inherit;
  font-weight: bold;
  color: inherit;
  text-rendering: optimizelegibility;
}
#force h1 small,
#force h2 small,
#force h3 small,
#force h4 small,
#force h5 small,
#force h6 small {
  font-weight: normal;
  color: #999999;
}
#force h1 {
  font-size: 30px;
  line-height: 36px;
}
#force h1 small {
  font-size: 18px;
}
#force h2 {
  font-size: 24px;
  line-height: 36px;
}
#force h2 small {
  font-size: 18px;
}
#force h3 {
  line-height: 27px;
  font-size: 18px;
}
#force h3 small {
  font-size: 14px;
}
#force h4,
#force h5,
#force h6 {
  line-height: 18px;
}
#force h4 {
  font-size: 14px;
}
#force h4 small {
  font-size: 12px;
}
#force h5 {
  font-size: 12px;
}
#force h6 {
  font-size: 11px;
  color: #999999;
  text-transform: uppercase;
}
#force .page-header {
  padding-bottom: 17px;
  margin: 18px 0;
  border-bottom: 1px solid #eeeeee;
}
#force .page-header h1 {
  line-height: 1;
}
#force ul,
#force ol {
  padding: 0;
  margin: 0 0 9px 25px;
}
#force ul ul,
#force ul ol,
#force ol ol,
#force ol ul {
  margin-bottom: 0;
}
#force ul {
  list-style: disc;
}
#force ol {
  list-style: decimal;
}
#force li {
  line-height: 18px;
}
#force ul.unstyled,
#force ol.unstyled {
  margin-left: 0;
  list-style: none;
}
#force dl {
  margin-bottom: 18px;
}
#force dt,
#force dd {
  line-height: 18px;
}
#force dt {
  font-weight: bold;
  line-height: 17px;
}
#force dd {
  margin-left: 9px;
}
#force .dl-horizontal dt {
  float: left;
  clear: left;
  width: 120px;
  text-align: right;
}
#force .dl-horizontal dd {
  margin-left: 130px;
}
#force hr {
  margin: 18px 0;
  border: 0;
  border-top: 1px solid #eeeeee;
  border-bottom: 1px solid #ffffff;
}
#force strong {
  font-weight: bold;
}
#force em {
  font-style: italic;
}
#force .muted {
  color: #999999;
}
#force abbr[title] {
  border-bottom: 1px dotted #ddd;
  cursor: help;
}
#force abbr.initialism {
  font-size: 90%;
  text-transform: uppercase;
}
#force blockquote {
  padding: 0 0 0 15px;
  margin: 0 0 18px;
  border-left: 5px solid #eeeeee;
}
#force blockquote p {
  margin-bottom: 0;
  font-size: 16px;
  font-weight: 300;
  line-height: 22.5px;
}
#force blockquote small {
  display: block;
  line-height: 18px;
  color: #999999;
}
#force blockquote small:before {
  content: '\2014 \00A0';
}
#force blockquote.pull-right {
  float: right;
  padding-left: 0;
  padding-right: 15px;
  border-left: 0;
  border-right: 5px solid #eeeeee;
}
#force blockquote.pull-right p,
#force blockquote.pull-right small {
  text-align: right;
}
#force q:before,
#force q:after,
#force blockquote:before,
#force blockquote:after {
  content: "";
}
#force address {
  display: block;
  margin-bottom: 18px;
  line-height: 18px;
  font-style: normal;
}
#force small {
  font-size: 100%;
}
#force cite {
  font-style: normal;
}
#force code,
#force pre {
  padding: 0 3px 2px;
  font-family: Menlo, Monaco, "Courier New", monospace;
  font-size: 12px;
  color: #333333;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
#force code {
  padding: 2px 4px;
  color: #d14;
  background-color: #f7f7f9;
  border: 1px solid #e1e1e8;
}
#force pre {
  display: block;
  padding: 8.5px;
  margin: 0 0 9px;
  font-size: 12.025px;
  line-height: 18px;
  background-color: #f5f5f5;
  border: 1px solid #ccc;
  border: 1px solid rgba(0, 0, 0, 0.15);
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  white-space: pre;
  white-space: pre-wrap;
  word-break: break-all;
  word-wrap: break-word;
}
#force pre.prettyprint {
  margin-bottom: 18px;
}
#force pre code {
  padding: 0;
  color: inherit;
  background-color: transparent;
  border: 0;
}
#force .pre-scrollable {
  max-height: 340px;
  overflow-y: scroll;
}
#force form {
  margin: 0 0 18px;
}
#force fieldset {
  padding: 0;
  margin: 0;
  border: 0;
}
#force legend {
  display: block;
  width: 100%;
  padding: 0;
  margin-bottom: 27px;
  font-size: 19.5px;
  line-height: 36px;
  color: #333333;
  border: 0;
  border-bottom: 1px solid #eee;
}
#force legend small {
  font-size: 13.5px;
  color: #999999;
}
#force label,
#force input,
#force button,
#force select,
#force textarea {
  font-size: 13px;
  font-weight: normal;
  line-height: 18px;
}
#force input,
#force button,
#force select,
#force textarea {
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}
#force label {
  display: block;
  margin-bottom: 5px;
  color: #333333;
}
#force input,
#force textarea,
#force select,
#force .uneditable-input {
  display: inline-block;
  width: 210px;
  height: 18px;
  padding: 4px;
  margin-bottom: 9px;
  font-size: 13px;
  line-height: 18px;
  color: #555555;
  border: 1px solid #cccccc;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
#force .uneditable-textarea {
  width: auto;
  height: auto;
}
#force label input,
#force label textarea,
#force label select {
  display: block;
}
#force input[type="image"],
#force input[type="checkbox"],
#force input[type="radio"] {
  width: auto;
  height: auto;
  padding: 0;
  margin: 3px 0;
  *margin-top: 0;
  /* IE7 */

  line-height: normal;
  cursor: pointer;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
  border: 0 \9;
  /* IE9 and down */

}
#force input[type="image"] {
  border: 0;
}
#force input[type="file"] {
  width: auto;
  padding: initial;
  line-height: initial;
  border: initial;
  background-color: #ffffff;
  background-color: initial;
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
  box-shadow: none;
}
#force input[type="button"],
#force input[type="reset"],
#force input[type="submit"] {
  width: auto;
  height: auto;
}
#force select,
#force input[type="file"] {
  height: 28px;
  /* In IE7, the height of the select element cannot be changed by height, only font-size */

  *margin-top: 4px;
  /* For IE7, add top margin to align select with labels */

  line-height: 28px;
}
#force input[type="file"] {
  line-height: 18px \9;
}
#force select {
  width: 220px;
  background-color: #ffffff;
}
#force select[multiple],
#force select[size] {
  height: auto;
}
#force input[type="image"] {
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
  box-shadow: none;
}
#force textarea {
  height: auto;
}
#force input[type="hidden"] {
  display: none;
}
#force .radio,
#force .checkbox {
  padding-left: 18px;
}
#force .radio input[type="radio"],
#force .checkbox input[type="checkbox"] {
  float: left;
  margin-left: -18px;
}
#force .controls > .radio:first-child,
#force .controls > .checkbox:first-child {
  padding-top: 5px;
}
#force .radio.inline,
#force .checkbox.inline {
  display: inline-block;
  padding-top: 5px;
  margin-bottom: 0;
  vertical-align: middle;
}
#force .radio.inline + .radio.inline,
#force .checkbox.inline + .checkbox.inline {
  margin-left: 10px;
}
#force input,
#force textarea {
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-transition: border linear 0.2s, box-shadow linear 0.2s;
  -moz-transition: border linear 0.2s, box-shadow linear 0.2s;
  -ms-transition: border linear 0.2s, box-shadow linear 0.2s;
  -o-transition: border linear 0.2s, box-shadow linear 0.2s;
  transition: border linear 0.2s, box-shadow linear 0.2s;
}
#force input:focus,
#force textarea:focus {
  border-color: rgba(82, 168, 236, 0.8);
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
  -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
  outline: 0;
  outline: thin dotted \9;
  /* IE6-9 */

}
#force input[type="file"]:focus,
#force input[type="radio"]:focus,
#force input[type="checkbox"]:focus,
#force select:focus {
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
  box-shadow: none;
  outline: thin dotted #333;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
#force .input-mini {
  width: 60px;
}
#force .input-small {
  width: 90px;
}
#force .input-medium {
  width: 150px;
}
#force .input-large {
  width: 210px;
}
#force .input-xlarge {
  width: 270px;
}
#force .input-xxlarge {
  width: 530px;
}
#force input[class*="span"],
#force select[class*="span"],
#force textarea[class*="span"],
#force .uneditable-input {
  float: none;
  margin-left: 0;
}
#force input,
#force textarea,
#force .uneditable-input {
  margin-left: 0;
}
#force input.span12, textarea.span12, .uneditable-input.span12 {
  width: 930px;
}
#force input.span11, textarea.span11, .uneditable-input.span11 {
  width: 850px;
}
#force input.span10, textarea.span10, .uneditable-input.span10 {
  width: 770px;
}
#force input.span9, textarea.span9, .uneditable-input.span9 {
  width: 690px;
}
#force input.span8, textarea.span8, .uneditable-input.span8 {
  width: 610px;
}
#force input.span7, textarea.span7, .uneditable-input.span7 {
  width: 530px;
}
#force input.span6, textarea.span6, .uneditable-input.span6 {
  width: 450px;
}
#force input.span5, textarea.span5, .uneditable-input.span5 {
  width: 370px;
}
#force input.span4, textarea.span4, .uneditable-input.span4 {
  width: 290px;
}
#force input.span3, textarea.span3, .uneditable-input.span3 {
  width: 210px;
}
#force input.span2, textarea.span2, .uneditable-input.span2 {
  width: 130px;
}
#force input.span1, textarea.span1, .uneditable-input.span1 {
  width: 50px;
}
#force input[disabled],
#force select[disabled],
#force textarea[disabled],
#force input[readonly],
#force select[readonly],
#force textarea[readonly] {
  background-color: #eeeeee;
  border-color: #ddd;
  cursor: not-allowed;
}
#force .control-group.warning > label,
#force .control-group.warning .help-block,
#force .control-group.warning .help-inline {
  color: #c09853;
}
#force .control-group.warning input,
#force .control-group.warning select,
#force .control-group.warning textarea {
  color: #c09853;
  border-color: #c09853;
}
#force .control-group.warning input:focus,
#force .control-group.warning select:focus,
#force .control-group.warning textarea:focus {
  border-color: #a47e3c;
  -webkit-box-shadow: 0 0 6px #dbc59e;
  -moz-box-shadow: 0 0 6px #dbc59e;
  box-shadow: 0 0 6px #dbc59e;
}
#force .control-group.warning .input-prepend .add-on,
#force .control-group.warning .input-append .add-on {
  color: #c09853;
  background-color: #fcf8e3;
  border-color: #c09853;
}
#force .control-group.error > label,
#force .control-group.error .help-block,
#force .control-group.error .help-inline {
  color: #b94a48;
}
#force .control-group.error input,
#force .control-group.error select,
#force .control-group.error textarea {
  color: #b94a48;
  border-color: #b94a48;
}
#force .control-group.error input:focus,
#force .control-group.error select:focus,
#force .control-group.error textarea:focus {
  border-color: #953b39;
  -webkit-box-shadow: 0 0 6px #d59392;
  -moz-box-shadow: 0 0 6px #d59392;
  box-shadow: 0 0 6px #d59392;
}
#force .control-group.error .input-prepend .add-on,
#force .control-group.error .input-append .add-on {
  color: #b94a48;
  background-color: #f2dede;
  border-color: #b94a48;
}
#force .control-group.success > label,
#force .control-group.success .help-block,
#force .control-group.success .help-inline {
  color: #468847;
}
#force .control-group.success input,
#force .control-group.success select,
#force .control-group.success textarea {
  color: #468847;
  border-color: #468847;
}
#force .control-group.success input:focus,
#force .control-group.success select:focus,
#force .control-group.success textarea:focus {
  border-color: #356635;
  -webkit-box-shadow: 0 0 6px #7aba7b;
  -moz-box-shadow: 0 0 6px #7aba7b;
  box-shadow: 0 0 6px #7aba7b;
}
#force .control-group.success .input-prepend .add-on,
#force .control-group.success .input-append .add-on {
  color: #468847;
  background-color: #dff0d8;
  border-color: #468847;
}
#force input:focus:required:invalid,
#force textarea:focus:required:invalid,
#force select:focus:required:invalid {
  color: #b94a48;
  border-color: #ee5f5b;
}
#force input:focus:required:invalid:focus,
#force textarea:focus:required:invalid:focus,
#force select:focus:required:invalid:focus {
  border-color: #e9322d;
  -webkit-box-shadow: 0 0 6px #f8b9b7;
  -moz-box-shadow: 0 0 6px #f8b9b7;
  box-shadow: 0 0 6px #f8b9b7;
}
#force .form-actions {
  padding: 17px 20px 18px;
  margin-top: 18px;
  margin-bottom: 18px;
  background-color: #eeeeee;
  border-top: 1px solid #ddd;
  *zoom: 1;
}
#force .form-actions:before,
#force .form-actions:after {
  display: table;
  content: "";
}
#force .form-actions:after {
  clear: both;
}
#force .uneditable-input {
  display: block;
  background-color: #ffffff;
  border-color: #eee;
  -webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.025);
  -moz-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.025);
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.025);
  cursor: not-allowed;
}
#force :-moz-placeholder {
  color: #999999;
}
#force ::-webkit-input-placeholder {
  color: #999999;
}
#force .help-block,
#force .help-inline {
  color: #555555;
}
#force .help-block {
  display: block;
  margin-bottom: 9px;
}
#force .help-inline {
  display: inline-block;
  *display: inline;
  /* IE7 inline-block hack */

  *zoom: 1;
  vertical-align: middle;
  padding-left: 5px;
}
#force .input-prepend,
#force .input-append {
  margin-bottom: 5px;
}
#force .input-prepend input,
#force .input-append input,
#force .input-prepend select,
#force .input-append select,
#force .input-prepend .uneditable-input,
#force .input-append .uneditable-input {
  *margin-left: 0;
  -webkit-border-radius: 0 3px 3px 0;
  -moz-border-radius: 0 3px 3px 0;
  border-radius: 0 3px 3px 0;
}
#force .input-prepend input:focus,
#force .input-append input:focus,
#force .input-prepend select:focus,
#force .input-append select:focus,
#force .input-prepend .uneditable-input:focus,
#force .input-append .uneditable-input:focus {
  position: relative;
  z-index: 2;
}
#force .input-prepend .uneditable-input,
#force .input-append .uneditable-input {
  border-left-color: #ccc;
}
#force .input-prepend .add-on,
#force .input-append .add-on {
  display: inline-block;
  width: auto;
  min-width: 16px;
  height: 18px;
  padding: 4px 5px;
  font-weight: normal;
  line-height: 18px;
  text-align: center;
  text-shadow: 0 1px 0 #ffffff;
  vertical-align: middle;
  background-color: #eeeeee;
  border: 1px solid #ccc;
}
#force .input-prepend .add-on,
#force .input-append .add-on,
#force .input-prepend .btn,
#force .input-append .btn {
  -webkit-border-radius: 3px 0 0 3px;
  -moz-border-radius: 3px 0 0 3px;
  border-radius: 3px 0 0 3px;
}
#force .input-prepend .active,
#force .input-append .active {
  background-color: #a9dba9;
  border-color: #46a546;
}
#force .input-prepend .add-on,
#force .input-prepend .btn {
  margin-right: -1px;
}
#force .input-append input,
#force .input-append select .uneditable-input {
  -webkit-border-radius: 3px 0 0 3px;
  -moz-border-radius: 3px 0 0 3px;
  border-radius: 3px 0 0 3px;
}
#force .input-append .uneditable-input {
  border-left-color: #eee;
  border-right-color: #ccc;
}
#force .input-append .add-on,
#force .input-append .btn {
  margin-left: -1px;
  -webkit-border-radius: 0 3px 3px 0;
  -moz-border-radius: 0 3px 3px 0;
  border-radius: 0 3px 3px 0;
}
#force .input-prepend.input-append input,
#force .input-prepend.input-append select,
#force .input-prepend.input-append .uneditable-input {
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
}
#force .input-prepend.input-append .add-on:first-child,
#force .input-prepend.input-append .btn:first-child {
  margin-right: -1px;
  -webkit-border-radius: 3px 0 0 3px;
  -moz-border-radius: 3px 0 0 3px;
  border-radius: 3px 0 0 3px;
}
#force .input-prepend.input-append .add-on:last-child,
#force .input-prepend.input-append .btn:last-child {
  margin-left: -1px;
  -webkit-border-radius: 0 3px 3px 0;
  -moz-border-radius: 0 3px 3px 0;
  border-radius: 0 3px 3px 0;
}
#force .search-query {
  padding-left: 14px;
  padding-right: 14px;
  margin-bottom: 0;
  -webkit-border-radius: 14px;
  -moz-border-radius: 14px;
  border-radius: 14px;
}
#force .form-search input,
#force .form-inline input,
#force .form-horizontal input,
#force .form-search textarea,
#force .form-inline textarea,
#force .form-horizontal textarea,
#force .form-search select,
#force .form-inline select,
#force .form-horizontal select,
#force .form-search .help-inline,
#force .form-inline .help-inline,
#force .form-horizontal .help-inline,
#force .form-search .uneditable-input,
#force .form-inline .uneditable-input,
#force .form-horizontal .uneditable-input,
#force .form-search .input-prepend,
#force .form-inline .input-prepend,
#force .form-horizontal .input-prepend,
#force .form-search .input-append,
#force .form-inline .input-append,
#force .form-horizontal .input-append {
  display: inline-block;
  margin-bottom: 0;
}
#force .form-search .hide,
#force .form-inline .hide,
#force .form-horizontal .hide {
  display: none;
}
#force .form-search label,
#force .form-inline label {
  display: inline-block;
}
#force .form-search .input-append,
#force .form-inline .input-append,
#force .form-search .input-prepend,
#force .form-inline .input-prepend {
  margin-bottom: 0;
}
#force .form-search .radio,
#force .form-search .checkbox,
#force .form-inline .radio,
#force .form-inline .checkbox {
  padding-left: 0;
  margin-bottom: 0;
  vertical-align: middle;
}
#force .form-search .radio input[type="radio"],
#force .form-search .checkbox input[type="checkbox"],
#force .form-inline .radio input[type="radio"],
#force .form-inline .checkbox input[type="checkbox"] {
  float: left;
  margin-left: 0;
  margin-right: 3px;
}
#force .control-group {
  margin-bottom: 9px;
}
#force legend + .control-group {
  margin-top: 18px;
  -webkit-margin-top-collapse: separate;
}
#force .form-horizontal .control-group {
  margin-bottom: 18px;
  *zoom: 1;
}
#force .form-horizontal .control-group:before,
#force .form-horizontal .control-group:after {
  display: table;
  content: "";
}
#force .form-horizontal .control-group:after {
  clear: both;
}
#force .form-horizontal .control-label {
  float: left;
  width: 140px;
  padding-top: 5px;
  text-align: right;
}
#force .form-horizontal .controls {
  margin-left: 160px;
  /* Super jank IE7 fix to ensure the inputs in .input-append and input-prepend don't inherit the margin of the parent, in this case .controls */

  *display: inline-block;
  *margin-left: 0;
  *padding-left: 20px;
}
#force .form-horizontal .help-block {
  margin-top: 9px;
  margin-bottom: 0;
}
#force .form-horizontal .form-actions {
  padding-left: 160px;
}
#force table {
  max-width: 100%;
  border-collapse: collapse;
  border-spacing: 0;
  background-color: transparent;
}
#force .table {
  width: 100%;
  margin-bottom: 18px;
}
#force .table th,
#force .table td {
  padding: 8px;
  line-height: 18px;
  text-align: left;
  vertical-align: top;
  border-top: 1px solid #dddddd;
}
#force .table th {
  font-weight: bold;
}
#force .table thead th {
  vertical-align: bottom;
}
#force .table colgroup + thead tr:first-child th,
#force .table colgroup + thead tr:first-child td,
#force .table thead:first-child tr:first-child th,
#force .table thead:first-child tr:first-child td {
  border-top: 0;
}
#force .table tbody + tbody {
  border-top: 2px solid #dddddd;
}
#force .table-condensed th,
#force .table-condensed td {
  padding: 4px 5px;
}
#force .table-bordered {
  border: 1px solid #dddddd;
  border-left: 0;
  border-collapse: separate;
  *border-collapse: collapsed;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .table-bordered th,
#force .table-bordered td {
  border-left: 1px solid #dddddd;
}
#force .table-bordered thead:first-child tr:first-child th,
#force .table-bordered tbody:first-child tr:first-child th,
#force .table-bordered tbody:first-child tr:first-child td {
  border-top: 0;
}
#force .table-bordered thead:first-child tr:first-child th:first-child,
#force .table-bordered tbody:first-child tr:first-child td:first-child {
  -webkit-border-radius: 4px 0 0 0;
  -moz-border-radius: 4px 0 0 0;
  border-radius: 4px 0 0 0;
}
#force .table-bordered thead:first-child tr:first-child th:last-child,
#force .table-bordered tbody:first-child tr:first-child td:last-child {
  -webkit-border-radius: 0 4px 0 0;
  -moz-border-radius: 0 4px 0 0;
  border-radius: 0 4px 0 0;
}
#force .table-bordered thead:last-child tr:last-child th:first-child,
#force .table-bordered tbody:last-child tr:last-child td:first-child {
  -webkit-border-radius: 0 0 0 4px;
  -moz-border-radius: 0 0 0 4px;
  border-radius: 0 0 0 4px;
}
#force .table-bordered thead:last-child tr:last-child th:last-child,
#force .table-bordered tbody:last-child tr:last-child td:last-child {
  -webkit-border-radius: 0 0 4px 0;
  -moz-border-radius: 0 0 4px 0;
  border-radius: 0 0 4px 0;
}
#force .table-striped tbody tr:nth-child(odd) td,
#force .table-striped tbody tr:nth-child(odd) th {
  background-color: #f9f9f9;
}
#force .table tbody tr:hover td,
#force .table tbody tr:hover th {
  background-color: #f5f5f5;
}
#force table .span1 {
  float: none;
  width: 44px;
  margin-left: 0;
}
#force table .span2 {
  float: none;
  width: 124px;
  margin-left: 0;
}
#force table .span3 {
  float: none;
  width: 204px;
  margin-left: 0;
}
#force table .span4 {
  float: none;
  width: 284px;
  margin-left: 0;
}
#force table .span5 {
  float: none;
  width: 364px;
  margin-left: 0;
}
#force table .span6 {
  float: none;
  width: 444px;
  margin-left: 0;
}
#force table .span7 {
  float: none;
  width: 524px;
  margin-left: 0;
}
#force table .span8 {
  float: none;
  width: 604px;
  margin-left: 0;
}
#force table .span9 {
  float: none;
  width: 684px;
  margin-left: 0;
}
#force table .span10 {
  float: none;
  width: 764px;
  margin-left: 0;
}
#force table .span11 {
  float: none;
  width: 844px;
  margin-left: 0;
}
#force table .span12 {
  float: none;
  width: 924px;
  margin-left: 0;
}
#force table .span13 {
  float: none;
  width: 1004px;
  margin-left: 0;
}
#force table .span14 {
  float: none;
  width: 1084px;
  margin-left: 0;
}
#force table .span15 {
  float: none;
  width: 1164px;
  margin-left: 0;
}
#force table .span16 {
  float: none;
  width: 1244px;
  margin-left: 0;
}
#force table .span17 {
  float: none;
  width: 1324px;
  margin-left: 0;
}
#force table .span18 {
  float: none;
  width: 1404px;
  margin-left: 0;
}
#force table .span19 {
  float: none;
  width: 1484px;
  margin-left: 0;
}
#force table .span20 {
  float: none;
  width: 1564px;
  margin-left: 0;
}
#force table .span21 {
  float: none;
  width: 1644px;
  margin-left: 0;
}
#force table .span22 {
  float: none;
  width: 1724px;
  margin-left: 0;
}
#force table .span23 {
  float: none;
  width: 1804px;
  margin-left: 0;
}
#force table .span24 {
  float: none;
  width: 1884px;
  margin-left: 0;
}
#force [class^="icon-"],
#force [class*=" icon-"] {
  display: inline-block;
  width: 14px;
  height: 14px;
  line-height: 14px;
  vertical-align: text-top;
  background-image: url("../img/glyphicons-halflings.png");
  background-position: 14px 14px;
  background-repeat: no-repeat;
  *margin-right: .3em;
}
#force [class^="icon-"]:last-child,
#force [class*=" icon-"]:last-child {
  *margin-left: 0;
}
#force .icon-white {
  background-image: url("../img/glyphicons-halflings-white.png");
}
#force .icon-glass {
  background-position: 0 0;
}
#force .icon-music {
  background-position: -24px 0;
}
#force .icon-search {
  background-position: -48px 0;
}
#force .icon-envelope {
  background-position: -72px 0;
}
#force .icon-heart {
  background-position: -96px 0;
}
#force .icon-star {
  background-position: -120px 0;
}
#force .icon-star-empty {
  background-position: -144px 0;
}
#force .icon-user {
  background-position: -168px 0;
}
#force .icon-film {
  background-position: -192px 0;
}
#force .icon-th-large {
  background-position: -216px 0;
}
#force .icon-th {
  background-position: -240px 0;
}
#force .icon-th-list {
  background-position: -264px 0;
}
#force .icon-ok {
  background-position: -288px 0;
}
#force .icon-remove {
  background-position: -312px 0;
}
#force .icon-zoom-in {
  background-position: -336px 0;
}
#force .icon-zoom-out {
  background-position: -360px 0;
}
#force .icon-off {
  background-position: -384px 0;
}
#force .icon-signal {
  background-position: -408px 0;
}
#force .icon-cog {
  background-position: -432px 0;
}
#force .icon-trash {
  background-position: -456px 0;
}
#force .icon-home {
  background-position: 0 -24px;
}
#force .icon-file {
  background-position: -24px -24px;
}
#force .icon-time {
  background-position: -48px -24px;
}
#force .icon-road {
  background-position: -72px -24px;
}
#force .icon-download-alt {
  background-position: -96px -24px;
}
#force .icon-download {
  background-position: -120px -24px;
}
#force .icon-upload {
  background-position: -144px -24px;
}
#force .icon-inbox {
  background-position: -168px -24px;
}
#force .icon-play-circle {
  background-position: -192px -24px;
}
#force .icon-repeat {
  background-position: -216px -24px;
}
#force .icon-refresh {
  background-position: -240px -24px;
}
#force .icon-list-alt {
  background-position: -264px -24px;
}
#force .icon-lock {
  background-position: -287px -24px;
}
#force .icon-flag {
  background-position: -312px -24px;
}
#force .icon-headphones {
  background-position: -336px -24px;
}
#force .icon-volume-off {
  background-position: -360px -24px;
}
#force .icon-volume-down {
  background-position: -384px -24px;
}
#force .icon-volume-up {
  background-position: -408px -24px;
}
#force .icon-qrcode {
  background-position: -432px -24px;
}
#force .icon-barcode {
  background-position: -456px -24px;
}
#force .icon-tag {
  background-position: 0 -48px;
}
#force .icon-tags {
  background-position: -25px -48px;
}
#force .icon-book {
  background-position: -48px -48px;
}
#force .icon-bookmark {
  background-position: -72px -48px;
}
#force .icon-print {
  background-position: -96px -48px;
}
#force .icon-camera {
  background-position: -120px -48px;
}
#force .icon-font {
  background-position: -144px -48px;
}
#force .icon-bold {
  background-position: -167px -48px;
}
#force .icon-italic {
  background-position: -192px -48px;
}
#force .icon-text-height {
  background-position: -216px -48px;
}
#force .icon-text-width {
  background-position: -240px -48px;
}
#force .icon-align-left {
  background-position: -264px -48px;
}
#force .icon-align-center {
  background-position: -288px -48px;
}
#force .icon-align-right {
  background-position: -312px -48px;
}
#force .icon-align-justify {
  background-position: -336px -48px;
}
#force .icon-list {
  background-position: -360px -48px;
}
#force .icon-indent-left {
  background-position: -384px -48px;
}
#force .icon-indent-right {
  background-position: -408px -48px;
}
#force .icon-facetime-video {
  background-position: -432px -48px;
}
#force .icon-picture {
  background-position: -456px -48px;
}
#force .icon-pencil {
  background-position: 0 -72px;
}
#force .icon-map-marker {
  background-position: -24px -72px;
}
#force .icon-adjust {
  background-position: -48px -72px;
}
#force .icon-tint {
  background-position: -72px -72px;
}
#force .icon-edit {
  background-position: -96px -72px;
}
#force .icon-share {
  background-position: -120px -72px;
}
#force .icon-check {
  background-position: -144px -72px;
}
#force .icon-move {
  background-position: -168px -72px;
}
#force .icon-step-backward {
  background-position: -192px -72px;
}
#force .icon-fast-backward {
  background-position: -216px -72px;
}
#force .icon-backward {
  background-position: -240px -72px;
}
#force .icon-play {
  background-position: -264px -72px;
}
#force .icon-pause {
  background-position: -288px -72px;
}
#force .icon-stop {
  background-position: -312px -72px;
}
#force .icon-forward {
  background-position: -336px -72px;
}
#force .icon-fast-forward {
  background-position: -360px -72px;
}
#force .icon-step-forward {
  background-position: -384px -72px;
}
#force .icon-eject {
  background-position: -408px -72px;
}
#force .icon-chevron-left {
  background-position: -432px -72px;
}
#force .icon-chevron-right {
  background-position: -456px -72px;
}
#force .icon-plus-sign {
  background-position: 0 -96px;
}
#force .icon-minus-sign {
  background-position: -24px -96px;
}
#force .icon-remove-sign {
  background-position: -48px -96px;
}
#force .icon-ok-sign {
  background-position: -72px -96px;
}
#force .icon-question-sign {
  background-position: -96px -96px;
}
#force .icon-info-sign {
  background-position: -120px -96px;
}
#force .icon-screenshot {
  background-position: -144px -96px;
}
#force .icon-remove-circle {
  background-position: -168px -96px;
}
#force .icon-ok-circle {
  background-position: -192px -96px;
}
#force .icon-ban-circle {
  background-position: -216px -96px;
}
#force .icon-arrow-left {
  background-position: -240px -96px;
}
#force .icon-arrow-right {
  background-position: -264px -96px;
}
#force .icon-arrow-up {
  background-position: -289px -96px;
}
#force .icon-arrow-down {
  background-position: -312px -96px;
}
#force .icon-share-alt {
  background-position: -336px -96px;
}
#force .icon-resize-full {
  background-position: -360px -96px;
}
#force .icon-resize-small {
  background-position: -384px -96px;
}
#force .icon-plus {
  background-position: -408px -96px;
}
#force .icon-minus {
  background-position: -433px -96px;
}
#force .icon-asterisk {
  background-position: -456px -96px;
}
#force .icon-exclamation-sign {
  background-position: 0 -120px;
}
#force .icon-gift {
  background-position: -24px -120px;
}
#force .icon-leaf {
  background-position: -48px -120px;
}
#force .icon-fire {
  background-position: -72px -120px;
}
#force .icon-eye-open {
  background-position: -96px -120px;
}
#force .icon-eye-close {
  background-position: -120px -120px;
}
#force .icon-warning-sign {
  background-position: -144px -120px;
}
#force .icon-plane {
  background-position: -168px -120px;
}
#force .icon-calendar {
  background-position: -192px -120px;
}
#force .icon-random {
  background-position: -216px -120px;
}
#force .icon-comment {
  background-position: -240px -120px;
}
#force .icon-magnet {
  background-position: -264px -120px;
}
#force .icon-chevron-up {
  background-position: -288px -120px;
}
#force .icon-chevron-down {
  background-position: -313px -119px;
}
#force .icon-retweet {
  background-position: -336px -120px;
}
#force .icon-shopping-cart {
  background-position: -360px -120px;
}
#force .icon-folder-close {
  background-position: -384px -120px;
}
#force .icon-folder-open {
  background-position: -408px -120px;
}
#force .icon-resize-vertical {
  background-position: -432px -119px;
}
#force .icon-resize-horizontal {
  background-position: -456px -118px;
}
#force .dropdown {
  position: relative;
}
#force .dropdown-toggle {
  *margin-bottom: -3px;
}
#force .dropdown-toggle:active,
#force .open .dropdown-toggle {
  outline: 0;
}
#force .caret {
  display: inline-block;
  width: 0;
  height: 0;
  vertical-align: top;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-top: 4px solid #000000;
  opacity: 0.3;
  filter: alpha(opacity=30);
  content: "";
}
#force .dropdown .caret {
  margin-top: 8px;
  margin-left: 2px;
}
#force .dropdown:hover .caret,
#force .open.dropdown .caret {
  opacity: 1;
  filter: alpha(opacity=100);
}
#force .dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000;
  float: left;
  display: none;
  min-width: 160px;
  padding: 4px 0;
  margin: 0;
  list-style: none;
  background-color: #ffffff;
  border-color: #ccc;
  border-color: rgba(0, 0, 0, 0.2);
  border-style: solid;
  border-width: 1px;
  -webkit-border-radius: 0 0 5px 5px;
  -moz-border-radius: 0 0 5px 5px;
  border-radius: 0 0 5px 5px;
  -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
  -webkit-background-clip: padding-box;
  -moz-background-clip: padding;
  background-clip: padding-box;
  *border-right-width: 2px;
  *border-bottom-width: 2px;
}
#force .dropdown-menu.pull-right {
  right: 0;
  left: auto;
}
#force .dropdown-menu .divider {
  height: 1px;
  margin: 8px 1px;
  overflow: hidden;
  background-color: #e5e5e5;
  border-bottom: 1px solid #ffffff;
  *width: 100%;
  *margin: -5px 0 5px;
}
#force .dropdown-menu a {
  display: block;
  padding: 3px 15px;
  clear: both;
  font-weight: normal;
  line-height: 18px;
  color: #333333;
  white-space: nowrap;
}
#force .dropdown-menu li > a:hover,
#force .dropdown-menu .active > a,
#force .dropdown-menu .active > a:hover {
  color: #ffffff;
  text-decoration: none;
  background-color: #0088cc;
}
#force .dropdown.open {
  *z-index: 1000;
}
#force .dropdown.open .dropdown-toggle {
  color: #ffffff;
  background: #ccc;
  background: rgba(0, 0, 0, 0.3);
}
#force .dropdown.open .dropdown-menu {
  display: block;
}
#force .pull-right .dropdown-menu {
  left: auto;
  right: 0;
}
#force .dropup .caret,
#force .navbar-fixed-bottom .dropdown .caret {
  border-top: 0;
  border-bottom: 4px solid #000000;
  content: "\2191";
}
#force .dropup .dropdown-menu,
#force .navbar-fixed-bottom .dropdown .dropdown-menu {
  top: auto;
  bottom: 100%;
  margin-bottom: 1px;
}
#force .typeahead {
  margin-top: 2px;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .well {
  min-height: 20px;
  padding: 19px;
  margin-bottom: 20px;
  background-color: #f5f5f5;
  border: 1px solid #eee;
  border: 1px solid rgba(0, 0, 0, 0.05);
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}
#force .well blockquote {
  border-color: #ddd;
  border-color: rgba(0, 0, 0, 0.15);
}
#force .well-large {
  padding: 24px;
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
}
#force .well-small {
  padding: 9px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
#force .fade {
  -webkit-transition: opacity 0.15s linear;
  -moz-transition: opacity 0.15s linear;
  -ms-transition: opacity 0.15s linear;
  -o-transition: opacity 0.15s linear;
  transition: opacity 0.15s linear;
  opacity: 0;
}
#force .fade.in {
  opacity: 1;
}
#force .collapse {
  -webkit-transition: height 0.35s ease;
  -moz-transition: height 0.35s ease;
  -ms-transition: height 0.35s ease;
  -o-transition: height 0.35s ease;
  transition: height 0.35s ease;
  position: relative;
  overflow: hidden;
  height: 0;
}
#force .collapse.in {
  height: auto;
}
#force .close {
  float: right;
  font-size: 20px;
  font-weight: bold;
  line-height: 18px;
  color: #000000;
  text-shadow: 0 1px 0 #ffffff;
  opacity: 0.2;
  filter: alpha(opacity=20);
}
#force .close:hover {
  color: #000000;
  text-decoration: none;
  opacity: 0.4;
  filter: alpha(opacity=40);
  cursor: pointer;
}
#force .btn {
  display: inline-block;
  *display: inline;
  /* IE7 inline-block hack */

  *zoom: 1;
  padding: 4px 10px 4px;
  margin-bottom: 0;
  font-size: 13px;
  line-height: 18px;
  color: #333333;
  text-align: center;
  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
  vertical-align: middle;
  background-color: #f5f5f5;
  background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
  background-image: -ms-linear-gradient(top, #ffffff, #e6e6e6);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
  background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
  background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
  background-image: linear-gradient(top, #ffffff, #e6e6e6);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#e6e6e6', GradientType=0);
  border-color: #e6e6e6 #e6e6e6 #bfbfbf;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
  border: 1px solid #cccccc;
  border-bottom-color: #b3b3b3;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  *margin-left: .3em;
}
#force .btn:hover,
#force .btn:active,
#force .btn.active,
#force .btn.disabled,
#force .btn[disabled] {
  background-color: #e6e6e6;
}
#force .btn:active,
#force .btn.active {
  background-color: #cccccc \9;
}
#force .btn:first-child {
  *margin-left: 0;
}
#force .btn:hover {
  color: #333333;
  text-decoration: none;
  background-color: #e6e6e6;
  background-position: 0 -15px;
  -webkit-transition: background-position 0.1s linear;
  -moz-transition: background-position 0.1s linear;
  -ms-transition: background-position 0.1s linear;
  -o-transition: background-position 0.1s linear;
  transition: background-position 0.1s linear;
}
#force .btn:focus {
  outline: thin dotted #333;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
#force .btn.active,
#force .btn:active {
  background-image: none;
  -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
  background-color: #e6e6e6;
  background-color: #d9d9d9 \9;
  outline: 0;
}
#force .btn.disabled,
#force .btn[disabled] {
  cursor: default;
  background-image: none;
  background-color: #e6e6e6;
  opacity: 0.65;
  filter: alpha(opacity=65);
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
  box-shadow: none;
}
#force .btn-large {
  padding: 9px 14px;
  font-size: 15px;
  line-height: normal;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}
#force .btn-large [class^="icon-"] {
  margin-top: 1px;
}
#force .btn-small {
  padding: 5px 9px;
  font-size: 11px;
  line-height: 16px;
}
#force .btn-small [class^="icon-"] {
  margin-top: -1px;
}
#force .btn-mini {
  padding: 2px 6px;
  font-size: 11px;
  line-height: 14px;
}
#force .btn-primary,
#force .btn-primary:hover,
#force .btn-warning,
#force .btn-warning:hover,
#force .btn-danger,
#force .btn-danger:hover,
#force .btn-success,
#force .btn-success:hover,
#force .btn-info,
#force .btn-info:hover,
#force .btn-inverse,
#force .btn-inverse:hover {
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  color: #ffffff;
}
#force .btn-primary.active,
#force .btn-warning.active,
#force .btn-danger.active,
#force .btn-success.active,
#force .btn-info.active,
#force .btn-inverse.active {
  color: rgba(255, 255, 255, 0.75);
}
#force .btn-primary {
  background-color: #0074cc;
  background-image: -moz-linear-gradient(top, #0088cc, #0055cc);
  background-image: -ms-linear-gradient(top, #0088cc, #0055cc);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc), to(#0055cc));
  background-image: -webkit-linear-gradient(top, #0088cc, #0055cc);
  background-image: -o-linear-gradient(top, #0088cc, #0055cc);
  background-image: linear-gradient(top, #0088cc, #0055cc);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#0088cc', endColorstr='#0055cc', GradientType=0);
  border-color: #0055cc #0055cc #003580;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
}
#force .btn-primary:hover,
#force .btn-primary:active,
#force .btn-primary.active,
#force .btn-primary.disabled,
#force .btn-primary[disabled] {
  background-color: #0055cc;
}
#force .btn-primary:active,
#force .btn-primary.active {
  background-color: #004099 \9;
}
#force .btn-warning {
  background-color: #faa732;
  background-image: -moz-linear-gradient(top, #fbb450, #f89406);
  background-image: -ms-linear-gradient(top, #fbb450, #f89406);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#fbb450), to(#f89406));
  background-image: -webkit-linear-gradient(top, #fbb450, #f89406);
  background-image: -o-linear-gradient(top, #fbb450, #f89406);
  background-image: linear-gradient(top, #fbb450, #f89406);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fbb450', endColorstr='#f89406', GradientType=0);
  border-color: #f89406 #f89406 #ad6704;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
}
#force .btn-warning:hover,
#force .btn-warning:active,
#force .btn-warning.active,
#force .btn-warning.disabled,
#force .btn-warning[disabled] {
  background-color: #f89406;
}
#force .btn-warning:active,
#force .btn-warning.active {
  background-color: #c67605 \9;
}
#force .btn-danger {
  background-color: #da4f49;
  background-image: -moz-linear-gradient(top, #ee5f5b, #bd362f);
  background-image: -ms-linear-gradient(top, #ee5f5b, #bd362f);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ee5f5b), to(#bd362f));
  background-image: -webkit-linear-gradient(top, #ee5f5b, #bd362f);
  background-image: -o-linear-gradient(top, #ee5f5b, #bd362f);
  background-image: linear-gradient(top, #ee5f5b, #bd362f);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ee5f5b', endColorstr='#bd362f', GradientType=0);
  border-color: #bd362f #bd362f #802420;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
}
#force .btn-danger:hover,
#force .btn-danger:active,
#force .btn-danger.active,
#force .btn-danger.disabled,
#force .btn-danger[disabled] {
  background-color: #bd362f;
}
#force .btn-danger:active,
#force .btn-danger.active {
  background-color: #942a25 \9;
}
#force .btn-success {
  background-color: #5bb75b;
  background-image: -moz-linear-gradient(top, #62c462, #51a351);
  background-image: -ms-linear-gradient(top, #62c462, #51a351);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#62c462), to(#51a351));
  background-image: -webkit-linear-gradient(top, #62c462, #51a351);
  background-image: -o-linear-gradient(top, #62c462, #51a351);
  background-image: linear-gradient(top, #62c462, #51a351);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#62c462', endColorstr='#51a351', GradientType=0);
  border-color: #51a351 #51a351 #387038;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
}
#force .btn-success:hover,
#force .btn-success:active,
#force .btn-success.active,
#force .btn-success.disabled,
#force .btn-success[disabled] {
  background-color: #51a351;
}
#force .btn-success:active,
#force .btn-success.active {
  background-color: #408140 \9;
}
#force .btn-info {
  background-color: #49afcd;
  background-image: -moz-linear-gradient(top, #5bc0de, #2f96b4);
  background-image: -ms-linear-gradient(top, #5bc0de, #2f96b4);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#5bc0de), to(#2f96b4));
  background-image: -webkit-linear-gradient(top, #5bc0de, #2f96b4);
  background-image: -o-linear-gradient(top, #5bc0de, #2f96b4);
  background-image: linear-gradient(top, #5bc0de, #2f96b4);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#5bc0de', endColorstr='#2f96b4', GradientType=0);
  border-color: #2f96b4 #2f96b4 #1f6377;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
}
#force .btn-info:hover,
#force .btn-info:active,
#force .btn-info.active,
#force .btn-info.disabled,
#force .btn-info[disabled] {
  background-color: #2f96b4;
}
#force .btn-info:active,
#force .btn-info.active {
  background-color: #24748c \9;
}
#force .btn-inverse {
  background-color: #414141;
  background-image: -moz-linear-gradient(top, #555555, #222222);
  background-image: -ms-linear-gradient(top, #555555, #222222);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#555555), to(#222222));
  background-image: -webkit-linear-gradient(top, #555555, #222222);
  background-image: -o-linear-gradient(top, #555555, #222222);
  background-image: linear-gradient(top, #555555, #222222);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#555555', endColorstr='#222222', GradientType=0);
  border-color: #222222 #222222 #000000;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
}
#force .btn-inverse:hover,
#force .btn-inverse:active,
#force .btn-inverse.active,
#force .btn-inverse.disabled,
#force .btn-inverse[disabled] {
  background-color: #222222;
}
#force .btn-inverse:active,
#force .btn-inverse.active {
  background-color: #080808 \9;
}
#force button.btn,
#force input[type="submit"].btn {
  *padding-top: 2px;
  *padding-bottom: 2px;
}
#force button.btn::-moz-focus-inner,
#force input[type="submit"].btn::-moz-focus-inner {
  padding: 0;
  border: 0;
}
#force button.btn.btn-large,
#force input[type="submit"].btn.btn-large {
  *padding-top: 7px;
  *padding-bottom: 7px;
}
#force button.btn.btn-small,
#force input[type="submit"].btn.btn-small {
  *padding-top: 3px;
  *padding-bottom: 3px;
}
#force button.btn.btn-mini,
#force input[type="submit"].btn.btn-mini {
  *padding-top: 1px;
  *padding-bottom: 1px;
}
#force .btn-group {
  position: relative;
  *zoom: 1;
  *margin-left: .3em;
}
#force .btn-group:before,
#force .btn-group:after {
  display: table;
  content: "";
}
#force .btn-group:after {
  clear: both;
}
#force .btn-group:first-child {
  *margin-left: 0;
}
#force .btn-group + .btn-group {
  margin-left: 5px;
}
#force .btn-toolbar {
  margin-top: 9px;
  margin-bottom: 9px;
}
#force .btn-toolbar .btn-group {
  display: inline-block;
  *display: inline;
  /* IE7 inline-block hack */

  *zoom: 1;
}
#force .btn-group .btn {
  position: relative;
  float: left;
  margin-left: -1px;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
}
#force .btn-group .btn:first-child {
  margin-left: 0;
  -webkit-border-top-left-radius: 4px;
  -moz-border-radius-topleft: 4px;
  border-top-left-radius: 4px;
  -webkit-border-bottom-left-radius: 4px;
  -moz-border-radius-bottomleft: 4px;
  border-bottom-left-radius: 4px;
}
#force .btn-group .btn:last-child,
#force .btn-group .dropdown-toggle {
  -webkit-border-top-right-radius: 4px;
  -moz-border-radius-topright: 4px;
  border-top-right-radius: 4px;
  -webkit-border-bottom-right-radius: 4px;
  -moz-border-radius-bottomright: 4px;
  border-bottom-right-radius: 4px;
}
#force .btn-group .btn.large:first-child {
  margin-left: 0;
  -webkit-border-top-left-radius: 6px;
  -moz-border-radius-topleft: 6px;
  border-top-left-radius: 6px;
  -webkit-border-bottom-left-radius: 6px;
  -moz-border-radius-bottomleft: 6px;
  border-bottom-left-radius: 6px;
}
#force .btn-group .btn.large:last-child,
#force .btn-group .large.dropdown-toggle {
  -webkit-border-top-right-radius: 6px;
  -moz-border-radius-topright: 6px;
  border-top-right-radius: 6px;
  -webkit-border-bottom-right-radius: 6px;
  -moz-border-radius-bottomright: 6px;
  border-bottom-right-radius: 6px;
}
#force .btn-group .btn:hover,
#force .btn-group .btn:focus,
#force .btn-group .btn:active,
#force .btn-group .btn.active {
  z-index: 2;
}
#force .btn-group .dropdown-toggle:active,
#force .btn-group.open .dropdown-toggle {
  outline: 0;
}
#force .btn-group .dropdown-toggle {
  padding-left: 8px;
  padding-right: 8px;
  -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  *padding-top: 3px;
  *padding-bottom: 3px;
}
#force .btn-group .btn-mini.dropdown-toggle {
  padding-left: 5px;
  padding-right: 5px;
  *padding-top: 1px;
  *padding-bottom: 1px;
}
#force .btn-group .btn-small.dropdown-toggle {
  *padding-top: 4px;
  *padding-bottom: 4px;
}
#force .btn-group .btn-large.dropdown-toggle {
  padding-left: 12px;
  padding-right: 12px;
}
#force .btn-group.open {
  *z-index: 1000;
}
#force .btn-group.open .dropdown-menu {
  display: block;
  margin-top: 1px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}
#force .btn-group.open .dropdown-toggle {
  background-image: none;
  -webkit-box-shadow: inset 0 1px 6px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 0 1px 6px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: inset 0 1px 6px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
}
#force .btn .caret {
  margin-top: 7px;
  margin-left: 0;
}
#force .btn:hover .caret,
#force .open.btn-group .caret {
  opacity: 1;
  filter: alpha(opacity=100);
}
#force .btn-mini .caret {
  margin-top: 5px;
}
#force .btn-small .caret {
  margin-top: 6px;
}
#force .btn-large .caret {
  margin-top: 6px;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 5px solid #000000;
}
#force .btn-primary .caret,
#force .btn-warning .caret,
#force .btn-danger .caret,
#force .btn-info .caret,
#force .btn-success .caret,
#force .btn-inverse .caret {
  border-top-color: #ffffff;
  border-bottom-color: #ffffff;
  opacity: 0.75;
  filter: alpha(opacity=75);
}
#force .alert {
  padding: 8px 35px 8px 14px;
  margin-bottom: 18px;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
  background-color: #fcf8e3;
  border: 1px solid #fbeed5;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  color: #c09853;
}
#force .alert-heading {
  color: inherit;
}
#force .alert .close {
  position: relative;
  top: -2px;
  right: -21px;
  line-height: 18px;
}
#force .alert-success {
  background-color: #dff0d8;
  border-color: #d6e9c6;
  color: #468847;
}
#force .alert-danger,
#force .alert-error {
  background-color: #f2dede;
  border-color: #eed3d7;
  color: #b94a48;
}
#force .alert-info {
  background-color: #d9edf7;
  border-color: #bce8f1;
  color: #3a87ad;
}
#force .alert-block {
  padding-top: 14px;
  padding-bottom: 14px;
}
#force .alert-block > p,
#force .alert-block > ul {
  margin-bottom: 0;
}
#force .alert-block p + p {
  margin-top: 5px;
}
#force .nav {
  margin-left: 0;
  margin-bottom: 18px;
  list-style: none;
}
#force .nav > li > a {
  display: block;
}
#force .nav > li > a:hover {
  text-decoration: none;
  background-color: #eeeeee;
}
#force .nav .nav-header {
  display: block;
  padding: 3px 15px;
  font-size: 11px;
  font-weight: bold;
  line-height: 18px;
  color: #999999;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
  text-transform: uppercase;
}
#force .nav li + .nav-header {
  margin-top: 9px;
}
#force .nav-list {
  padding-left: 15px;
  padding-right: 15px;
  margin-bottom: 0;
}
#force .nav-list > li > a,
#force .nav-list .nav-header {
  margin-left: -15px;
  margin-right: -15px;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
}
#force .nav-list > li > a {
  padding: 3px 15px;
}
#force .nav-list > .active > a,
#force .nav-list > .active > a:hover {
  color: #ffffff;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.2);
  background-color: #0088cc;
}
#force .nav-list [class^="icon-"] {
  margin-right: 2px;
}
#force .nav-list .divider {
  height: 1px;
  margin: 8px 1px;
  overflow: hidden;
  background-color: #e5e5e5;
  border-bottom: 1px solid #ffffff;
  *width: 100%;
  *margin: -5px 0 5px;
}
#force .nav-tabs,
#force .nav-pills {
  *zoom: 1;
}
#force .nav-tabs:before,
#force .nav-pills:before,
#force .nav-tabs:after,
#force .nav-pills:after {
  display: table;
  content: "";
}
#force .nav-tabs:after,
#force .nav-pills:after {
  clear: both;
}
#force .nav-tabs > li,
#force .nav-pills > li {
  float: left;
}
#force .nav-tabs > li > a,
#force .nav-pills > li > a {
  padding-right: 12px;
  padding-left: 12px;
  margin-right: 2px;
  line-height: 14px;
}
#force .nav-tabs {
  border-bottom: 1px solid #ddd;
}
#force .nav-tabs > li {
  margin-bottom: -1px;
}
#force .nav-tabs > li > a {
  padding-top: 8px;
  padding-bottom: 8px;
  line-height: 18px;
  border: 1px solid transparent;
  -webkit-border-radius: 4px 4px 0 0;
  -moz-border-radius: 4px 4px 0 0;
  border-radius: 4px 4px 0 0;
}
#force .nav-tabs > li > a:hover {
  border-color: #eeeeee #eeeeee #dddddd;
}
#force .nav-tabs > .active > a,
#force .nav-tabs > .active > a:hover {
  color: #555555;
  background-color: #ffffff;
  border: 1px solid #ddd;
  border-bottom-color: transparent;
  cursor: default;
}
#force .nav-pills > li > a {
  padding-top: 8px;
  padding-bottom: 8px;
  margin-top: 2px;
  margin-bottom: 2px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}
#force .nav-pills > .active > a,
#force .nav-pills > .active > a:hover {
  color: #ffffff;
  background-color: #0088cc;
}
#force .nav-stacked > li {
  float: none;
}
#force .nav-stacked > li > a {
  margin-right: 0;
}
#force .nav-tabs.nav-stacked {
  border-bottom: 0;
}
#force .nav-tabs.nav-stacked > li > a {
  border: 1px solid #ddd;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
}
#force .nav-tabs.nav-stacked > li:first-child > a {
  -webkit-border-radius: 4px 4px 0 0;
  -moz-border-radius: 4px 4px 0 0;
  border-radius: 4px 4px 0 0;
}
#force .nav-tabs.nav-stacked > li:last-child > a {
  -webkit-border-radius: 0 0 4px 4px;
  -moz-border-radius: 0 0 4px 4px;
  border-radius: 0 0 4px 4px;
}
#force .nav-tabs.nav-stacked > li > a:hover {
  border-color: #ddd;
  z-index: 2;
}
#force .nav-pills.nav-stacked > li > a {
  margin-bottom: 3px;
}
#force .nav-pills.nav-stacked > li:last-child > a {
  margin-bottom: 1px;
}
#force .nav-tabs .dropdown-menu,
#force .nav-pills .dropdown-menu {
  margin-top: 1px;
  border-width: 1px;
}
#force .nav-pills .dropdown-menu {
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .nav-tabs .dropdown-toggle .caret,
#force .nav-pills .dropdown-toggle .caret {
  border-top-color: #0088cc;
  border-bottom-color: #0088cc;
  margin-top: 6px;
}
#force .nav-tabs .dropdown-toggle:hover .caret,
#force .nav-pills .dropdown-toggle:hover .caret {
  border-top-color: #005580;
  border-bottom-color: #005580;
}
#force .nav-tabs .active .dropdown-toggle .caret,
#force .nav-pills .active .dropdown-toggle .caret {
  border-top-color: #333333;
  border-bottom-color: #333333;
}
#force .nav > .dropdown.active > a:hover {
  color: #000000;
  cursor: pointer;
}
#force .nav-tabs .open .dropdown-toggle,
#force .nav-pills .open .dropdown-toggle,
#force .nav > .open.active > a:hover {
  color: #ffffff;
  background-color: #999999;
  border-color: #999999;
}
#force .nav .open .caret,
#force .nav .open.active .caret,
#force .nav .open a:hover .caret {
  border-top-color: #ffffff;
  border-bottom-color: #ffffff;
  opacity: 1;
  filter: alpha(opacity=100);
}
#force .tabs-stacked .open > a:hover {
  border-color: #999999;
}
#force .tabbable {
  *zoom: 1;
}
#force .tabbable:before,
#force .tabbable:after {
  display: table;
  content: "";
}
#force .tabbable:after {
  clear: both;
}
#force .tab-content {
  display: table;
  width: 100%;
}
#force .tabs-below .nav-tabs,
#force .tabs-right .nav-tabs,
#force .tabs-left .nav-tabs {
  border-bottom: 0;
}
#force .tab-content > .tab-pane,
#force .pill-content > .pill-pane {
  display: none;
}
#force .tab-content > .active,
#force .pill-content > .active {
  display: block;
}
#force .tabs-below .nav-tabs {
  border-top: 1px solid #ddd;
}
#force .tabs-below .nav-tabs > li {
  margin-top: -1px;
  margin-bottom: 0;
}
#force .tabs-below .nav-tabs > li > a {
  -webkit-border-radius: 0 0 4px 4px;
  -moz-border-radius: 0 0 4px 4px;
  border-radius: 0 0 4px 4px;
}
#force .tabs-below .nav-tabs > li > a:hover {
  border-bottom-color: transparent;
  border-top-color: #ddd;
}
#force .tabs-below .nav-tabs .active > a,
#force .tabs-below .nav-tabs .active > a:hover {
  border-color: transparent #ddd #ddd #ddd;
}
#force .tabs-left .nav-tabs > li,
#force .tabs-right .nav-tabs > li {
  float: none;
}
#force .tabs-left .nav-tabs > li > a,
#force .tabs-right .nav-tabs > li > a {
  min-width: 74px;
  margin-right: 0;
  margin-bottom: 3px;
}
#force .tabs-left .nav-tabs {
  float: left;
  margin-right: 19px;
  border-right: 1px solid #ddd;
}
#force .tabs-left .nav-tabs > li > a {
  margin-right: -1px;
  -webkit-border-radius: 4px 0 0 4px;
  -moz-border-radius: 4px 0 0 4px;
  border-radius: 4px 0 0 4px;
}
#force .tabs-left .nav-tabs > li > a:hover {
  border-color: #eeeeee #dddddd #eeeeee #eeeeee;
}
#force .tabs-left .nav-tabs .active > a,
#force .tabs-left .nav-tabs .active > a:hover {
  border-color: #ddd transparent #ddd #ddd;
  *border-right-color: #ffffff;
}
#force .tabs-right .nav-tabs {
  float: right;
  margin-left: 19px;
  border-left: 1px solid #ddd;
}
#force .tabs-right .nav-tabs > li > a {
  margin-left: -1px;
  -webkit-border-radius: 0 4px 4px 0;
  -moz-border-radius: 0 4px 4px 0;
  border-radius: 0 4px 4px 0;
}
#force .tabs-right .nav-tabs > li > a:hover {
  border-color: #eeeeee #eeeeee #eeeeee #dddddd;
}
#force .tabs-right .nav-tabs .active > a,
#force .tabs-right .nav-tabs .active > a:hover {
  border-color: #ddd #ddd #ddd transparent;
  *border-left-color: #ffffff;
}
#force .navbar {
  *position: relative;
  *z-index: 2;
  overflow: visible;
  margin-bottom: 18px;
}
#force .navbar-inner {
  padding-left: 20px;
  padding-right: 20px;
  background-color: #2c2c2c;
  background-image: -moz-linear-gradient(top, #333333, #222222);
  background-image: -ms-linear-gradient(top, #333333, #222222);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#333333), to(#222222));
  background-image: -webkit-linear-gradient(top, #333333, #222222);
  background-image: -o-linear-gradient(top, #333333, #222222);
  background-image: linear-gradient(top, #333333, #222222);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#333333', endColorstr='#222222', GradientType=0);
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  -webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25), inset 0 -1px 0 rgba(0, 0, 0, 0.1);
  -moz-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25), inset 0 -1px 0 rgba(0, 0, 0, 0.1);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25), inset 0 -1px 0 rgba(0, 0, 0, 0.1);
}
#force .navbar .container {
  width: auto;
}
#force .btn-navbar {
  display: none;
  float: right;
  padding: 7px 10px;
  margin-left: 5px;
  margin-right: 5px;
  background-color: #2c2c2c;
  background-image: -moz-linear-gradient(top, #333333, #222222);
  background-image: -ms-linear-gradient(top, #333333, #222222);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#333333), to(#222222));
  background-image: -webkit-linear-gradient(top, #333333, #222222);
  background-image: -o-linear-gradient(top, #333333, #222222);
  background-image: linear-gradient(top, #333333, #222222);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#333333', endColorstr='#222222', GradientType=0);
  border-color: #222222 #222222 #000000;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  filter: progid:dximagetransform.microsoft.gradient(enabled=false);
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
}
#force .btn-navbar:hover,
#force .btn-navbar:active,
#force .btn-navbar.active,
#force .btn-navbar.disabled,
#force .btn-navbar[disabled] {
  background-color: #222222;
}
#force .btn-navbar:active,
#force .btn-navbar.active {
  background-color: #080808 \9;
}
#force .btn-navbar .icon-bar {
  display: block;
  width: 18px;
  height: 2px;
  background-color: #f5f5f5;
  -webkit-border-radius: 1px;
  -moz-border-radius: 1px;
  border-radius: 1px;
  -webkit-box-shadow: 0 1px 0 rgba(0, 0, 0, 0.25);
  -moz-box-shadow: 0 1px 0 rgba(0, 0, 0, 0.25);
  box-shadow: 0 1px 0 rgba(0, 0, 0, 0.25);
}
#force .btn-navbar .icon-bar + .icon-bar {
  margin-top: 3px;
}
#force .nav-collapse.collapse {
  height: auto;
}
#force .navbar {
  color: #999999;
}
#force .navbar .brand:hover {
  text-decoration: none;
}
#force .navbar .brand {
  float: left;
  display: block;
  padding: 8px 20px 12px;
  margin-left: -20px;
  font-size: 20px;
  font-weight: 200;
  line-height: 1;
  color: #ffffff;
}
#force .navbar .navbar-text {
  margin-bottom: 0;
  line-height: 40px;
}
#force .navbar .btn,
#force .navbar .btn-group {
  margin-top: 5px;
}
#force .navbar .btn-group .btn {
  margin-top: 0;
}
#force .navbar-form {
  margin-bottom: 0;
  *zoom: 1;
}
#force .navbar-form:before,
#force .navbar-form:after {
  display: table;
  content: "";
}
#force .navbar-form:after {
  clear: both;
}
#force .navbar-form input,
#force .navbar-form select,
#force .navbar-form .radio,
#force .navbar-form .checkbox {
  margin-top: 5px;
}
#force .navbar-form input,
#force .navbar-form select {
  display: inline-block;
  margin-bottom: 0;
}
#force .navbar-form input[type="image"],
#force .navbar-form input[type="checkbox"],
#force .navbar-form input[type="radio"] {
  margin-top: 3px;
}
#force .navbar-form .input-append,
#force .navbar-form .input-prepend {
  margin-top: 6px;
  white-space: nowrap;
}
#force .navbar-form .input-append input,
#force .navbar-form .input-prepend input {
  margin-top: 0;
}
#force .navbar-search {
  position: relative;
  float: left;
  margin-top: 6px;
  margin-bottom: 0;
}
#force .navbar-search .search-query {
  padding: 4px 9px;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  font-weight: normal;
  line-height: 1;
  color: #ffffff;
  background-color: #626262;
  border: 1px solid #151515;
  -webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1), 0 1px 0px rgba(255, 255, 255, 0.15);
  -moz-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1), 0 1px 0px rgba(255, 255, 255, 0.15);
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1), 0 1px 0px rgba(255, 255, 255, 0.15);
  -webkit-transition: none;
  -moz-transition: none;
  -ms-transition: none;
  -o-transition: none;
  transition: none;
}
#force .navbar-search .search-query:-moz-placeholder {
  color: #cccccc;
}
#force .navbar-search .search-query::-webkit-input-placeholder {
  color: #cccccc;
}
#force .navbar-search .search-query:focus,
#force .navbar-search .search-query.focused {
  padding: 5px 10px;
  color: #333333;
  text-shadow: 0 1px 0 #ffffff;
  background-color: #ffffff;
  border: 0;
  -webkit-box-shadow: 0 0 3px rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 0 3px rgba(0, 0, 0, 0.15);
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.15);
  outline: 0;
}
#force .navbar-fixed-top,
#force .navbar-fixed-bottom {
  position: fixed;
  right: 0;
  left: 0;
  z-index: 1030;
  margin-bottom: 0;
}
#force .navbar-fixed-top .navbar-inner,
#force .navbar-fixed-bottom .navbar-inner {
  padding-left: 0;
  padding-right: 0;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
}
#force .navbar-fixed-top .container,
#force .navbar-fixed-bottom .container {
  width: 940px;
}
#force .navbar-fixed-top {
  top: 0;
}
#force .navbar-fixed-bottom {
  bottom: 0;
}
#force .navbar .nav {
  position: relative;
  left: 0;
  display: block;
  float: left;
  margin: 0 10px 0 0;
}
#force .navbar .nav.pull-right {
  float: right;
}
#force .navbar .nav > li {
  display: block;
  float: left;
}
#force .navbar .nav > li > a {
  float: none;
  padding: 10px 10px 11px;
  line-height: 19px;
  color: #999999;
  text-decoration: none;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
}
#force .navbar .nav > li > a:hover {
  background-color: transparent;
  color: #ffffff;
  text-decoration: none;
}
#force .navbar .nav .active > a,
#force .navbar .nav .active > a:hover {
  color: #ffffff;
  text-decoration: none;
  background-color: #222222;
}
#force .navbar .divider-vertical {
  height: 40px;
  width: 1px;
  margin: 0 9px;
  overflow: hidden;
  background-color: #222222;
  border-right: 1px solid #333333;
}
#force .navbar .nav.pull-right {
  margin-left: 10px;
  margin-right: 0;
}
#force .navbar .dropdown-menu {
  margin-top: 1px;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .navbar .dropdown-menu:before {
  content: '';
  display: inline-block;
  border-left: 7px solid transparent;
  border-right: 7px solid transparent;
  border-bottom: 7px solid #ccc;
  border-bottom-color: rgba(0, 0, 0, 0.2);
  position: absolute;
  top: -7px;
  left: 9px;
}
#force .navbar .dropdown-menu:after {
  content: '';
  display: inline-block;
  border-left: 6px solid transparent;
  border-right: 6px solid transparent;
  border-bottom: 6px solid #ffffff;
  position: absolute;
  top: -6px;
  left: 10px;
}
#force .navbar-fixed-bottom .dropdown-menu:before {
  border-top: 7px solid #ccc;
  border-top-color: rgba(0, 0, 0, 0.2);
  border-bottom: 0;
  bottom: -7px;
  top: auto;
}
#force .navbar-fixed-bottom .dropdown-menu:after {
  border-top: 6px solid #ffffff;
  border-bottom: 0;
  bottom: -6px;
  top: auto;
}
#force .navbar .nav .dropdown-toggle .caret,
#force .navbar .nav .open.dropdown .caret {
  border-top-color: #ffffff;
  border-bottom-color: #ffffff;
}
#force .navbar .nav .active .caret {
  opacity: 1;
  filter: alpha(opacity=100);
}
#force .navbar .nav .open > .dropdown-toggle,
#force .navbar .nav .active > .dropdown-toggle,
#force .navbar .nav .open.active > .dropdown-toggle {
  background-color: transparent;
}
#force .navbar .nav .active > .dropdown-toggle:hover {
  color: #ffffff;
}
#force .navbar .nav.pull-right .dropdown-menu,
#force .navbar .nav .dropdown-menu.pull-right {
  left: auto;
  right: 0;
}
#force .navbar .nav.pull-right .dropdown-menu:before,
#force .navbar .nav .dropdown-menu.pull-right:before {
  left: auto;
  right: 12px;
}
#force .navbar .nav.pull-right .dropdown-menu:after,
#force .navbar .nav .dropdown-menu.pull-right:after {
  left: auto;
  right: 13px;
}
#force .breadcrumb {
  padding: 7px 14px;
  margin: 0 0 18px;
  list-style: none;
  background-color: #fbfbfb;
  background-image: -moz-linear-gradient(top, #ffffff, #f5f5f5);
  background-image: -ms-linear-gradient(top, #ffffff, #f5f5f5);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#f5f5f5));
  background-image: -webkit-linear-gradient(top, #ffffff, #f5f5f5);
  background-image: -o-linear-gradient(top, #ffffff, #f5f5f5);
  background-image: linear-gradient(top, #ffffff, #f5f5f5);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#f5f5f5', GradientType=0);
  border: 1px solid #ddd;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  -webkit-box-shadow: inset 0 1px 0 #ffffff;
  -moz-box-shadow: inset 0 1px 0 #ffffff;
  box-shadow: inset 0 1px 0 #ffffff;
}
#force .breadcrumb li {
  display: inline-block;
  *display: inline;
  /* IE7 inline-block hack */

  *zoom: 1;
  text-shadow: 0 1px 0 #ffffff;
}
#force .breadcrumb .divider {
  padding: 0 5px;
  color: #999999;
}
#force .breadcrumb .active a {
  color: #333333;
}
#force .pagination {
  height: 36px;
  margin: 18px 0;
}
#force .pagination ul {
  display: inline-block;
  *display: inline;
  /* IE7 inline-block hack */

  *zoom: 1;
  margin-left: 0;
  margin-bottom: 0;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}
#force .pagination li {
  display: inline;
}
#force .pagination a {
  float: left;
  padding: 0 14px;
  line-height: 34px;
  text-decoration: none;
  border: 1px solid #ddd;
  border-left-width: 0;
}
#force .pagination a:hover,
#force .pagination .active a {
  background-color: #f5f5f5;
}
#force .pagination .active a {
  color: #999999;
  cursor: default;
}
#force .pagination .disabled span,
#force .pagination .disabled a,
#force .pagination .disabled a:hover {
  color: #999999;
  background-color: transparent;
  cursor: default;
}
#force .pagination li:first-child a {
  border-left-width: 1px;
  -webkit-border-radius: 3px 0 0 3px;
  -moz-border-radius: 3px 0 0 3px;
  border-radius: 3px 0 0 3px;
}
#force .pagination li:last-child a {
  -webkit-border-radius: 0 3px 3px 0;
  -moz-border-radius: 0 3px 3px 0;
  border-radius: 0 3px 3px 0;
}
#force .pagination-centered {
  text-align: center;
}
#force .pagination-right {
  text-align: right;
}
#force .pager {
  margin-left: 0;
  margin-bottom: 18px;
  list-style: none;
  text-align: center;
  *zoom: 1;
}
#force .pager:before,
#force .pager:after {
  display: table;
  content: "";
}
#force .pager:after {
  clear: both;
}
#force .pager li {
  display: inline;
}
#force .pager a {
  display: inline-block;
  padding: 5px 14px;
  background-color: #fff;
  border: 1px solid #ddd;
  -webkit-border-radius: 15px;
  -moz-border-radius: 15px;
  border-radius: 15px;
}
#force .pager a:hover {
  text-decoration: none;
  background-color: #f5f5f5;
}
#force .pager .next a {
  float: right;
}
#force .pager .previous a {
  float: left;
}
#force .pager .disabled a,
#force .pager .disabled a:hover {
  color: #999999;
  background-color: #fff;
  cursor: default;
}
#force .modal-open .dropdown-menu {
  z-index: 2050;
}
#force .modal-open .dropdown.open {
  *z-index: 2050;
}
#force .modal-open .popover {
  z-index: 2060;
}
#force .modal-open .tooltip {
  z-index: 2070;
}
#force .modal-backdrop {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 1040;
  background-color: #000000;
}
#force .modal-backdrop.fade {
  opacity: 0;
}
#force .modal-backdrop,
#force .modal-backdrop.fade.in {
  opacity: 0.8;
  filter: alpha(opacity=80);
}
#force .modal {
  position: fixed;
  top: 50%;
  left: 50%;
  z-index: 1050;
  overflow: auto;
  width: 560px;
  margin: -250px 0 0 -280px;
  background-color: #ffffff;
  border: 1px solid #999;
  border: 1px solid rgba(0, 0, 0, 0.3);
  *border: 1px solid #999;
  /* IE6-7 */

  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
  -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
  box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
  -webkit-background-clip: padding-box;
  -moz-background-clip: padding-box;
  background-clip: padding-box;
}
#force .modal.fade {
  -webkit-transition: opacity .3s linear, top .3s ease-out;
  -moz-transition: opacity .3s linear, top .3s ease-out;
  -ms-transition: opacity .3s linear, top .3s ease-out;
  -o-transition: opacity .3s linear, top .3s ease-out;
  transition: opacity .3s linear, top .3s ease-out;
  top: -25%;
}
#force .modal.fade.in {
  top: 50%;
}
#force .modal-header {
  padding: 9px 15px;
  border-bottom: 1px solid #eee;
}
#force .modal-header .close {
  margin-top: 2px;
}
#force .modal-body {
  overflow-y: auto;
  max-height: 400px;
  padding: 15px;
}
#force .modal-form {
  margin-bottom: 0;
}
#force .modal-footer {
  padding: 14px 15px 15px;
  margin-bottom: 0;
  text-align: right;
  background-color: #f5f5f5;
  border-top: 1px solid #ddd;
  -webkit-border-radius: 0 0 6px 6px;
  -moz-border-radius: 0 0 6px 6px;
  border-radius: 0 0 6px 6px;
  -webkit-box-shadow: inset 0 1px 0 #ffffff;
  -moz-box-shadow: inset 0 1px 0 #ffffff;
  box-shadow: inset 0 1px 0 #ffffff;
  *zoom: 1;
}
#force .modal-footer:before,
#force .modal-footer:after {
  display: table;
  content: "";
}
#force .modal-footer:after {
  clear: both;
}
#force .modal-footer .btn + .btn {
  margin-left: 5px;
  margin-bottom: 0;
}
#force .modal-footer .btn-group .btn + .btn {
  margin-left: -1px;
}
#force .tooltip {
  position: absolute;
  z-index: 1020;
  display: block;
  visibility: visible;
  padding: 5px;
  font-size: 11px;
  opacity: 0;
  filter: alpha(opacity=0);
}
#force .tooltip.in {
  opacity: 0.8;
  filter: alpha(opacity=80);
}
#force .tooltip.top {
  margin-top: -2px;
}
#force .tooltip.right {
  margin-left: 2px;
}
#force .tooltip.bottom {
  margin-top: 2px;
}
#force .tooltip.left {
  margin-left: -2px;
}
#force .tooltip.top .tooltip-arrow {
  bottom: 0;
  left: 50%;
  margin-left: -5px;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 5px solid #000000;
}
#force .tooltip.left .tooltip-arrow {
  top: 50%;
  right: 0;
  margin-top: -5px;
  border-top: 5px solid transparent;
  border-bottom: 5px solid transparent;
  border-left: 5px solid #000000;
}
#force .tooltip.bottom .tooltip-arrow {
  top: 0;
  left: 50%;
  margin-left: -5px;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-bottom: 5px solid #000000;
}
#force .tooltip.right .tooltip-arrow {
  top: 50%;
  left: 0;
  margin-top: -5px;
  border-top: 5px solid transparent;
  border-bottom: 5px solid transparent;
  border-right: 5px solid #000000;
}
#force .tooltip-inner {
  max-width: 200px;
  padding: 3px 8px;
  color: #ffffff;
  text-align: center;
  text-decoration: none;
  background-color: #000000;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .tooltip-arrow {
  position: absolute;
  width: 0;
  height: 0;
}
#force .popover {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1010;
  display: none;
  padding: 5px;
}
#force .popover.top {
  margin-top: -5px;
}
#force .popover.right {
  margin-left: 5px;
}
#force .popover.bottom {
  margin-top: 5px;
}
#force .popover.left {
  margin-left: -5px;
}
#force .popover.top .arrow {
  bottom: 0;
  left: 50%;
  margin-left: -5px;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 5px solid #000000;
}
#force .popover.right .arrow {
  top: 50%;
  left: 0;
  margin-top: -5px;
  border-top: 5px solid transparent;
  border-bottom: 5px solid transparent;
  border-right: 5px solid #000000;
}
#force .popover.bottom .arrow {
  top: 0;
  left: 50%;
  margin-left: -5px;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-bottom: 5px solid #000000;
}
#force .popover.left .arrow {
  top: 50%;
  right: 0;
  margin-top: -5px;
  border-top: 5px solid transparent;
  border-bottom: 5px solid transparent;
  border-left: 5px solid #000000;
}
#force .popover .arrow {
  position: absolute;
  width: 0;
  height: 0;
}
#force .popover-inner {
  padding: 3px;
  width: 280px;
  overflow: hidden;
  background: #000000;
  background: rgba(0, 0, 0, 0.8);
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
  -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
  box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
}
#force .popover-title {
  padding: 9px 15px;
  line-height: 1;
  background-color: #f5f5f5;
  border-bottom: 1px solid #eee;
  -webkit-border-radius: 3px 3px 0 0;
  -moz-border-radius: 3px 3px 0 0;
  border-radius: 3px 3px 0 0;
}
#force .popover-content {
  padding: 14px;
  background-color: #ffffff;
  -webkit-border-radius: 0 0 3px 3px;
  -moz-border-radius: 0 0 3px 3px;
  border-radius: 0 0 3px 3px;
  -webkit-background-clip: padding-box;
  -moz-background-clip: padding-box;
  background-clip: padding-box;
}
#force .popover-content p,
#force .popover-content ul,
#force .popover-content ol {
  margin-bottom: 0;
}
#force .thumbnails {
  margin-left: -20px;
  list-style: none;
  *zoom: 1;
}
#force .thumbnails:before,
#force .thumbnails:after {
  display: table;
  content: "";
}
#force .thumbnails:after {
  clear: both;
}
#force .thumbnails > li {
  float: left;
  margin: 0 0 18px 20px;
}
#force .thumbnail {
  display: block;
  padding: 4px;
  line-height: 1;
  border: 1px solid #ddd;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
  -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
}
#force a.thumbnail:hover {
  border-color: #0088cc;
  -webkit-box-shadow: 0 1px 4px rgba(0, 105, 214, 0.25);
  -moz-box-shadow: 0 1px 4px rgba(0, 105, 214, 0.25);
  box-shadow: 0 1px 4px rgba(0, 105, 214, 0.25);
}
#force .thumbnail > img {
  display: block;
  max-width: 100%;
  margin-left: auto;
  margin-right: auto;
}
#force .thumbnail .caption {
  padding: 9px;
}
#force .label {
  padding: 1px 4px 2px;
  font-size: 10.998px;
  font-weight: bold;
  line-height: 13px;
  color: #ffffff;
  vertical-align: middle;
  white-space: nowrap;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  background-color: #999999;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
#force .label:hover {
  color: #ffffff;
  text-decoration: none;
}
#force .label-important {
  background-color: #b94a48;
}
#force .label-important:hover {
  background-color: #953b39;
}
#force .label-warning {
  background-color: #f89406;
}
#force .label-warning:hover {
  background-color: #c67605;
}
#force .label-success {
  background-color: #468847;
}
#force .label-success:hover {
  background-color: #356635;
}
#force .label-info {
  background-color: #3a87ad;
}
#force .label-info:hover {
  background-color: #2d6987;
}
#force .label-inverse {
  background-color: #333333;
}
#force .label-inverse:hover {
  background-color: #1a1a1a;
}
#force .badge {
  padding: 1px 9px 2px;
  font-size: 12.025px;
  font-weight: bold;
  white-space: nowrap;
  color: #ffffff;
  background-color: #999999;
  -webkit-border-radius: 9px;
  -moz-border-radius: 9px;
  border-radius: 9px;
}
#force .badge:hover {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
}
#force .badge-error {
  background-color: #b94a48;
}
#force .badge-error:hover {
  background-color: #953b39;
}
#force .badge-warning {
  background-color: #f89406;
}
#force .badge-warning:hover {
  background-color: #c67605;
}
#force .badge-success {
  background-color: #468847;
}
#force .badge-success:hover {
  background-color: #356635;
}
#force .badge-info {
  background-color: #3a87ad;
}
#force .badge-info:hover {
  background-color: #2d6987;
}
#force .badge-inverse {
  background-color: #333333;
}
#force .badge-inverse:hover {
  background-color: #1a1a1a;
}
#force @-webkit-keyframes progress-bar-stripes {
  from {
    background-position: 0 0;
  }
#force to {
    background-position: 40px 0;
  }
#force }
#force @-moz-keyframes progress-bar-stripes {
  from {
    background-position: 0 0;
  }
#force to {
    background-position: 40px 0;
  }
#force }
#force @-ms-keyframes progress-bar-stripes {
  from {
    background-position: 0 0;
  }
#force to {
    background-position: 40px 0;
  }
#force }
#force @keyframes progress-bar-stripes {
  from {
    background-position: 0 0;
  }
#force to {
    background-position: 40px 0;
  }
#force }
#force .progress {
  overflow: hidden;
  height: 18px;
  margin-bottom: 18px;
  background-color: #f7f7f7;
  background-image: -moz-linear-gradient(top, #f5f5f5, #f9f9f9);
  background-image: -ms-linear-gradient(top, #f5f5f5, #f9f9f9);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#f5f5f5), to(#f9f9f9));
  background-image: -webkit-linear-gradient(top, #f5f5f5, #f9f9f9);
  background-image: -o-linear-gradient(top, #f5f5f5, #f9f9f9);
  background-image: linear-gradient(top, #f5f5f5, #f9f9f9);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f5f5f5', endColorstr='#f9f9f9', GradientType=0);
  -webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
  -moz-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .progress .bar {
  width: 0%;
  height: 18px;
  color: #ffffff;
  font-size: 12px;
  text-align: center;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  background-color: #0e90d2;
  background-image: -moz-linear-gradient(top, #149bdf, #0480be);
  background-image: -ms-linear-gradient(top, #149bdf, #0480be);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#149bdf), to(#0480be));
  background-image: -webkit-linear-gradient(top, #149bdf, #0480be);
  background-image: -o-linear-gradient(top, #149bdf, #0480be);
  background-image: linear-gradient(top, #149bdf, #0480be);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#149bdf', endColorstr='#0480be', GradientType=0);
  -webkit-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
  box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  -ms-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: width 0.6s ease;
  -moz-transition: width 0.6s ease;
  -ms-transition: width 0.6s ease;
  -o-transition: width 0.6s ease;
  transition: width 0.6s ease;
}
#force .progress-striped .bar {
  background-color: #149bdf;
  background-image: -webkit-gradient(linear, 0 100%, 100% 0, color-stop(0.25, rgba(255, 255, 255, 0.15)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.15)), color-stop(0.75, rgba(255, 255, 255, 0.15)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  -webkit-background-size: 40px 40px;
  -moz-background-size: 40px 40px;
  -o-background-size: 40px 40px;
  background-size: 40px 40px;
}
#force .progress.active .bar {
  -webkit-animation: progress-bar-stripes 2s linear infinite;
  -moz-animation: progress-bar-stripes 2s linear infinite;
  animation: progress-bar-stripes 2s linear infinite;
}
#force .progress-danger .bar {
  background-color: #dd514c;
  background-image: -moz-linear-gradient(top, #ee5f5b, #c43c35);
  background-image: -ms-linear-gradient(top, #ee5f5b, #c43c35);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ee5f5b), to(#c43c35));
  background-image: -webkit-linear-gradient(top, #ee5f5b, #c43c35);
  background-image: -o-linear-gradient(top, #ee5f5b, #c43c35);
  background-image: linear-gradient(top, #ee5f5b, #c43c35);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ee5f5b', endColorstr='#c43c35', GradientType=0);
}
#force .progress-danger.progress-striped .bar {
  background-color: #ee5f5b;
  background-image: -webkit-gradient(linear, 0 100%, 100% 0, color-stop(0.25, rgba(255, 255, 255, 0.15)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.15)), color-stop(0.75, rgba(255, 255, 255, 0.15)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
#force .progress-success .bar {
  background-color: #5eb95e;
  background-image: -moz-linear-gradient(top, #62c462, #57a957);
  background-image: -ms-linear-gradient(top, #62c462, #57a957);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#62c462), to(#57a957));
  background-image: -webkit-linear-gradient(top, #62c462, #57a957);
  background-image: -o-linear-gradient(top, #62c462, #57a957);
  background-image: linear-gradient(top, #62c462, #57a957);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#62c462', endColorstr='#57a957', GradientType=0);
}
#force .progress-success.progress-striped .bar {
  background-color: #62c462;
  background-image: -webkit-gradient(linear, 0 100%, 100% 0, color-stop(0.25, rgba(255, 255, 255, 0.15)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.15)), color-stop(0.75, rgba(255, 255, 255, 0.15)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
#force .progress-info .bar {
  background-color: #4bb1cf;
  background-image: -moz-linear-gradient(top, #5bc0de, #339bb9);
  background-image: -ms-linear-gradient(top, #5bc0de, #339bb9);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#5bc0de), to(#339bb9));
  background-image: -webkit-linear-gradient(top, #5bc0de, #339bb9);
  background-image: -o-linear-gradient(top, #5bc0de, #339bb9);
  background-image: linear-gradient(top, #5bc0de, #339bb9);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#5bc0de', endColorstr='#339bb9', GradientType=0);
}
#force .progress-info.progress-striped .bar {
  background-color: #5bc0de;
  background-image: -webkit-gradient(linear, 0 100%, 100% 0, color-stop(0.25, rgba(255, 255, 255, 0.15)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.15)), color-stop(0.75, rgba(255, 255, 255, 0.15)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
#force .progress-warning .bar {
  background-color: #faa732;
  background-image: -moz-linear-gradient(top, #fbb450, #f89406);
  background-image: -ms-linear-gradient(top, #fbb450, #f89406);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#fbb450), to(#f89406));
  background-image: -webkit-linear-gradient(top, #fbb450, #f89406);
  background-image: -o-linear-gradient(top, #fbb450, #f89406);
  background-image: linear-gradient(top, #fbb450, #f89406);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fbb450', endColorstr='#f89406', GradientType=0);
}
#force .progress-warning.progress-striped .bar {
  background-color: #fbb450;
  background-image: -webkit-gradient(linear, 0 100%, 100% 0, color-stop(0.25, rgba(255, 255, 255, 0.15)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.15)), color-stop(0.75, rgba(255, 255, 255, 0.15)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
  background-image: linear-gradient(-45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
}
#force .accordion {
  margin-bottom: 18px;
}
#force .accordion-group {
  margin-bottom: 2px;
  border: 1px solid #e5e5e5;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
#force .accordion-heading {
  border-bottom: 0;
}
#force .accordion-heading .accordion-toggle {
  display: block;
  padding: 8px 15px;
}
#force .accordion-inner {
  padding: 9px 15px;
  border-top: 1px solid #e5e5e5;
}
#force .carousel {
  position: relative;
  margin-bottom: 18px;
  line-height: 1;
}
#force .carousel-inner {
  overflow: hidden;
  width: 100%;
  position: relative;
}
#force .carousel .item {
  display: none;
  position: relative;
  -webkit-transition: 0.6s ease-in-out left;
  -moz-transition: 0.6s ease-in-out left;
  -ms-transition: 0.6s ease-in-out left;
  -o-transition: 0.6s ease-in-out left;
  transition: 0.6s ease-in-out left;
}
#force .carousel .item > img {
  display: block;
  line-height: 1;
}
#force .carousel .active,
#force .carousel .next,
#force .carousel .prev {
  display: block;
}
#force .carousel .active {
  left: 0;
}
#force .carousel .next,
#force .carousel .prev {
  position: absolute;
  top: 0;
  width: 100%;
}
#force .carousel .next {
  left: 100%;
}
#force .carousel .prev {
  left: -100%;
}
#force .carousel .next.left,
#force .carousel .prev.right {
  left: 0;
}
#force .carousel .active.left {
  left: -100%;
}
#force .carousel .active.right {
  left: 100%;
}
#force .carousel-control {
  position: absolute;
  top: 40%;
  left: 15px;
  width: 40px;
  height: 40px;
  margin-top: -20px;
  font-size: 60px;
  font-weight: 100;
  line-height: 30px;
  color: #ffffff;
  text-align: center;
  background: #222222;
  border: 3px solid #ffffff;
  -webkit-border-radius: 23px;
  -moz-border-radius: 23px;
  border-radius: 23px;
  opacity: 0.5;
  filter: alpha(opacity=50);
}
#force .carousel-control.right {
  left: auto;
  right: 15px;
}
#force .carousel-control:hover {
  color: #ffffff;
  text-decoration: none;
  opacity: 0.9;
  filter: alpha(opacity=90);
}
#force .carousel-caption {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  padding: 10px 15px 5px;
  background: #333333;
  background: rgba(0, 0, 0, 0.75);
}
#force .carousel-caption h4,
#force .carousel-caption p {
  color: #ffffff;
}
#force .hero-unit {
  padding: 60px;
  margin-bottom: 30px;
  background-color: #eeeeee;
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
}
#force .hero-unit h1 {
  margin-bottom: 0;
  font-size: 60px;
  line-height: 1;
  color: inherit;
  letter-spacing: -1px;
}
#force .hero-unit p {
  font-size: 18px;
  font-weight: 200;
  line-height: 27px;
  color: inherit;
}
#force .pull-right {
  float: right;
}
#force .pull-left {
  float: left;
}
#force .hide {
  display: none;
}
#force .show {
  display: block;
}
#force .invisible {
  visibility: hidden;
}
#force ul li { margin-left: 0px; }