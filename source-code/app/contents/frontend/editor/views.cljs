
(ns app.contents.frontend.editor.views
    (:require [app.common.frontend.api  :as common]
              [app.storage.frontend.api :as storage]
              [forms.api                :as forms]
              [layouts.surface-a.api    :as surface-a]
              [plugins.item-editor.api  :as item-editor]
              [plugins.text-editor.api  :as text-editor]
              [x.app-core.api           :as a]
              [x.app-elements.api       :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-content-editor
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :contents.content-editor])]
       [text-editor/body ::content-content-editor
                         {:disabled?  editor-disabled?
                          :indent     {:vertical :xs :top :l}
                          :label      :body
                          :value-path [:contents :content-editor/edited-item :body]}]))

(defn- content-content
  []
  [content-content-editor])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-visibility-radio-button
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :contents.content-editor])]
       [elements/radio-button ::content-visibility-radio-button
                              {:disabled?       editor-disabled?
                               :indent          {:top :l :vertical :xs}
                               :label           :content-visibility
                               :options         [{:label :public-content  :helper :visible-to-everyone     :value :public}
                                                 {:label :private-content :helper :only-visible-to-editors :value :private}]
                               :option-helper-f :helper
                               :option-label-f  :label
                               :option-value-f  :value
                               :value-path      [:contents :content-editor/edited-item :visibility]}]))

(defn- content-name-field
  []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :contents.content-editor])]
       [elements/combo-box ::content-name-field
                           {:autofocus?   true
                            :disabled?    editor-disabled?
                            :emptiable?   false
                            :indent       {:top :l :vertical :xs}
                            :label        :name
                            :options-path [:contents :content-editor/suggestions :name]
                            :placeholder  :content-name
                            :required?    true
                            :value-path   [:contents :content-editor/edited-item :name]}]))

(defn- content-data
  []
  [:<> [:div (forms/form-row-attributes)
             [:div (forms/form-block-attributes {:ratio 100})
                   [content-name-field]]]
       [content-visibility-radio-button]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-editor-ghost-view :contents.content-editor
                                 {:padding "0 12px"}])

(defn- menu-bar
  []
  [common/item-editor-menu-bar :contents.content-editor
                               {:menu-items [{:label   :data
                                              :view-id :data
                                              :change-keys [:name]}
                                             {:label   :content
                                              :view-id :content
                                              :change-keys [:body]}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :contents.content-editor])]
       [:<> [menu-bar]
            (case current-view-id :data    [content-data]
                                  :content [content-content])]))

(defn- content-editor
  []
  [item-editor/body :contents.content-editor
                    {:auto-title?      true
                     :form-element     #'view-selector
                     :ghost-element    #'ghost-view
                     :initial-item     {:visibility :public}
                     :item-path        [:contents :content-editor/edited-item]
                     :label-key        :name
                     :suggestion-keys  [:name]
                     :suggestions-path [:contents :content-editor/suggestions]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [content-name @(a/subscribe [:db/get-item [:contents :content-editor/edited-item :name]])
        content-id   @(a/subscribe [:router/get-current-route-path-param :item-id])]
       [common/item-editor-breadcrumbs :contents.content-editor
                                       {:crumbs (if content-id [{:label :app-home
                                                                 :route "/@app-home"}
                                                                {:label :contents
                                                                 :route "/@app-home/contents"}
                                                                {:label       content-name
                                                                 :placeholder :unnamed-content
                                                                 :route       (str "/@app-home/contents/" content-id)}
                                                                {:label :edit!}]
                                                               [{:label :app-home
                                                                 :route "/@app-home"}
                                                                {:label :contents
                                                                 :route "/@app-home/contents"}
                                                                {:label :add!}])}]))

(defn- label-bar
  []
  (let [content-name @(a/subscribe [:db/get-item [:contents :content-editor/edited-item :name]])]
       [common/item-editor-label-bar :contents.content-editor
                                     {:label       content-name
                                      :placeholder :unnamed-content}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [content-editor]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
