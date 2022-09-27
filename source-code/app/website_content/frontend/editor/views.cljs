
(ns app.website-content.frontend.editor.views
    (:require [app.common.frontend.api   :as common]
              [app.contents.frontend.api :as contents]
              [app.storage.frontend.api  :as storage]
              [forms.api                 :as forms]
              [layouts.surface-a.api     :as surface-a]
              [mid-fruits.css            :as css]
              [mid-fruits.vector         :as vector]
              [plugins.file-editor.api   :as file-editor]
              [x.app-core.api            :as a]
              [x.app-elements.api        :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- address-data-information
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [contents/content-picker ::address-data-information-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:top :l :vertical :xs}
                                 :label      :address-data-information
                                 :value-path [:website-content :content-editor/edited-item :address-data-information]}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts-data-information
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [contents/content-picker ::contacts-data-information-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:top :l :vertical :xs}
                                 :label      :contacts-data-information
                                 :value-path [:website-content :content-editor/edited-item :contacts-data-information]}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- rent-informations
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [contents/content-picker ::rent-informations-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:top :l :vertical :xs}
                                 :label      :rent-informations
                                 :value-path [:website-content :content-editor/edited-item :rent-informations]}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- about-us
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [contents/content-picker ::about-us-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:top :l :vertical :xs}
                                 :label      :about-us
                                 :value-path [:website-content :content-editor/edited-item :about-us]}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- main-page
  []
  [:<> [rent-informations]
       [about-us]
       [contacts-data-information]
       [address-data-information]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- brand-logo-picker
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [storage/media-picker {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:all :xs}
                              :label         :logo
                              :multi-select? false
                              :toggle-label  :select-image!
                              :thumbnails    {:height :2xl :width :4xl}
                              :value-path    [:website-content :content-editor/edited-item :more-brands brand-dex :logo]}]))

(defn- duplicate-brand-button
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [elements/button {:color     :primary
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :xs :bottom :xxs}
                         :label     :duplicate!
                         :on-click  [:db/apply-item! [:website-content :content-editor/edited-item :more-brands]
                                                     vector/duplicate-nth-item brand-dex]}]))

(defn- delete-brand-button
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [elements/button {:color     :warning
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :xs :bottom :xxs}
                         :label     :delete!
                         :on-click  [:db/apply-item! [:website-content :content-editor/edited-item :more-brands]
                                                     vector/remove-nth-item brand-dex]}]))

(defn- brand-label-field
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [elements/text-field {:autofocus?  true
                             :disabled?   editor-disabled?
                             :label       :label
                             :indent      {:all :xs}
                             :value-path  [:website-content :content-editor/edited-item :more-brands brand-dex :label]}]))

(defn- brand-link-field
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [elements/text-field {:disabled?   editor-disabled?
                             :label       :link
                             :indent      {:all :xs}
                             :value-path  [:website-content :content-editor/edited-item :more-brands brand-dex :link]}]))

(defn- brand
  [brand-dex brand-props]
  [:<> [elements/horizontal-separator {:size :l}]
       [:div {:style {:background-color (css/var "background-color-highlight")
                      :border-radius    (css/var "border-radius-m")
                      :margin "0 12px"}}
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 30})
                         [brand-logo-picker brand-dex brand-props]]
                   [:div (forms/form-block-attributes {:ratio 70})
                         [brand-label-field brand-dex brand-props]
                         [brand-link-field brand-dex brand-props]]]
             [:div {:style {:display :flex :justify-content :flex-end}}
                   [duplicate-brand-button brand-dex brand-props]
                   [delete-brand-button    brand-dex brand-props]]]])

(defn- more-brands-list
  []
  (letfn [(f [%1 %2 %3] (conj %1 [brand %2 %3]))]
         (let [more-brands @(a/subscribe [:db/get-item [:website-content :content-editor/edited-item :more-brands]])]
              (reduce-kv f [:<>] more-brands))))

(defn- more-brands-action-bar
  []
  [common/file-editor-action-bar :website-content
                                 {:label    :add-brand!
                                  :on-click [:db/apply-item! [:website-content :content-editor/edited-item :more-brands]
                                                             vector/cons-item {}]}])

(defn- more-brands
  []
  [:<> [more-brands-action-bar]
       [more-brands-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [common/file-editor-menu-bar :website-content
                                    {:menu-items [{:label :main-page   :change-keys [:address-data-information :contacts-data-information
                                                                                     :about-us :rent-informations]}
                                                  {:label :more-brands :change-keys [:more-brands]}]
                                     :disabled? editor-disabled?}]))

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-content])]
       (case current-view-id :main-page   [main-page]
                             :more-brands [more-brands])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [common/file-editor-control-bar :website-content
                                       {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [common/surface-breadcrumbs :website-content/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :website-content}]
                                    :disabled? editor-disabled?}]))

(defn- label-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content])]
       [common/surface-label :website-content/view
                             {:disabled? editor-disabled?
                              :label     :website-content}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- website-content-editor
  []
  [file-editor/body :website-content
                    {:content-path  [:website-content :content-editor/edited-item]
                     :form-element  #'view-structure
                     :ghost-element #'common/file-editor-ghost-view}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-content-editor}])
