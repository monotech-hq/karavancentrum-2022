
(ns app.contents.frontend.api
    (:require [app.contents.frontend.editor.effects]
              [app.contents.frontend.lifecycles]
              [app.contents.frontend.lister.effects]
              [app.contents.frontend.lister.lifecycles]
              [app.contents.frontend.picker.effects]
              [app.contents.frontend.picker.events]
              [app.contents.frontend.picker.subs]
              [app.contents.frontend.selector.effects]
              [app.contents.frontend.selector.events]
              [app.contents.frontend.selector.subs]
              [app.contents.frontend.viewer.effects]
              [app.contents.frontend.picker.views :as picker.views]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.contents.frontend.picker.views
(def content-picker picker.views/element)
