
(ns app.website-menu.frontend.editor.boxes
    (:require [app.common.frontend.api     :as common]
              [app.components.frontend.api :as components]
              [elements.api                :as elements]
              [forms.api                   :as forms]
              [re-frame.api                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- add-link-button
  []
  [:div {:style {:display :flex}}
        [elements/button ::add-link-button
                         {:color     :muted
                          :font-size :xs
                          :indent    {:vertical :s}
                          :label     :add-link!
                          :on-click  [:website-menu/add-link!]}]])

(defn- website-menu-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-menu.editor])]
       [components/surface-box ::website-menu-box
                               {:content [:<> [add-link-button]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})]]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :label     :website-menu}]))
