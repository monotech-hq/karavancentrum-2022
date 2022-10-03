
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

(defn- about-us-picker
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [contents/content-picker ::about-us-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:vertical :s}
                                 :value-path [:website-content :editor/edited-item :about-us]}]))

(defn- about-us
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-box ::about-us
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 100})
                                                      [about-us-picker]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :about-us}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- address-data-information-picker
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [contents/content-picker ::address-data-information-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:vertical :s}
                                 :value-path [:website-content :editor/edited-item :address-data-information]}]))

(defn- address-data-information
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-box ::address-data-information
                           {:indent  {:top :m}
                            :content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 100})
                                                      [address-data-information-picker]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :address-data-information}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts-data-information-picker
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [contents/content-picker ::contacts-data-information-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:vertical :s}
                                 :value-path [:website-content :editor/edited-item :contacts-data-information]}]))

(defn- contacts-data-information
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-box ::contacts-data-information
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 100})
                                                      [contacts-data-information-picker]]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :contacts-data-information}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts
  []
  [:<> [contacts-data-information]
       [address-data-information]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- webshop-link-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/text-field ::webshop-link-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :link
                             :placeholder :webshop-link-placeholder
                             :value-path  [:website-content :editor/edited-item :webshop-link]}]))

(defn- webshop-settings
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-box ::webshop-settings
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 50})
                                                      [webshop-link-field]]
                                                [:div (forms/form-block-attributes {:ratio 50})]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :webshop}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- webshop
  []
  [:<> [webshop-settings]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- duplicate-brand-button
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/button {:color     :primary
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :s :top :m}
                         :label     :duplicate!
                         :on-click  [:db/apply-item! [:website-content :editor/edited-item :brands]
                                                     vector/duplicate-nth-item brand-dex]}]))

(defn- delete-brand-button
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/button {:color     :warning
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :s :top :m}
                         :label     :delete!
                         :on-click  [:db/apply-item! [:website-content :editor/edited-item :brands]
                                                     vector/remove-nth-item brand-dex]}]))

(defn- brand-description-field
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/multiline-field {:disabled?   editor-disabled?
                                  :label       :description
                                  :indent      {:top :m :vertical :s}
                                  :placeholder :brand-description-placeholder
                                  :value-path  [:website-content :editor/edited-item :brands brand-dex :description]}]))

(defn- brand-logo-picker
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [storage/media-picker {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:top :m :vertical :s}
                              :label         :logo
                              :multi-select? false
                              :toggle-label  :select-image!
                              :thumbnail     {:height :3xl :width :5xl}
                              :value-path    [:website-content :editor/edited-item :brands brand-dex :logo]}]))

(defn- brand-label-field
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/text-field {:disabled?   editor-disabled?
                             :label       :label
                             :indent      {:top :m :vertical :s}
                             :placeholder :brand-name
                             :value-path  [:website-content :editor/edited-item :brands brand-dex :label]}]))

(defn- brand-link-field
  [brand-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [elements/text-field {:disabled?   editor-disabled?
                             :label       :link
                             :indent      {:top :m :vertical :s}
                             :placeholder :website-link-placeholder
                             :value-path  [:website-content :editor/edited-item :brands brand-dex :link]}]))

(defn- brand
  [brand-dex brand-props]
  [common/surface-box {:indent  {:top :m}
                       :content [:<> [:div (forms/form-row-attributes)
                                           [:div (forms/form-block-attributes {:ratio 30})
                                                 [brand-logo-picker brand-dex brand-props]]
                                           [:div (forms/form-block-attributes {:ratio 70})
                                                 [brand-label-field brand-dex brand-props]
                                                 [brand-link-field  brand-dex brand-props]]]
                                     [:div (forms/form-row-attributes)
                                           [:div (forms/form-block-attributes {:ratio 100})
                                                 [brand-description-field brand-dex brand-props]]]
                                     [:div {:style {:display :flex :justify-content :flex-end}}
                                           [duplicate-brand-button brand-dex brand-props]
                                           [delete-brand-button    brand-dex brand-props]]
                                     [elements/horizontal-separator {:size :xs}]]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- brand-list
  []
  (letfn [(f [%1 %2 %3] (conj %1 [brand %2 %3]))]
         (let [brands @(a/subscribe [:db/get-item [:website-content :editor/edited-item :brands]])]
              (reduce-kv f [:<>] brands))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- brand-controls-action-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])
        on-click [:db/apply-item! [:website-content :editor/edited-item :brands] vector/cons-item {}]]
       [common/action-bar ::brand-controls-action-bar
                          {:disabled? editor-disabled?
                           :label     :add-brand!
                           :on-click  on-click}]))

(defn- brand-controls
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-box ::brand-controls
                           {:content [:<> [brand-controls-action-bar]
                                          [elements/horizontal-separator {:size :xs}]]
                            :disabled? editor-disabled?
                            :label     :selling}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- selling
  []
  [:<> [brand-controls]
       [brand-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- rent-informations-picker
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [contents/content-picker ::rent-informations-picker
                                {:disabled?  editor-disabled?
                                 :indent     {:vertical :s}
                                 :value-path [:website-content :editor/edited-item :rent-informations]}]))

(defn- rent-informations
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-box ::rent-informations
                           {:content [:<> [:div (forms/form-row-attributes)
                                                [:div (forms/form-block-attributes {:ratio 33})
                                                      [rent-informations-picker]]
                                                [:div (forms/form-block-attributes {:ratio 67})]]
                                          [elements/horizontal-separator {:size :s}]]
                            :disabled? editor-disabled?
                            :label     :rent-informations}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- renting
  []
  [:<> [rent-informations]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/file-editor-menu-bar :website-content.editor
                                    {:menu-items [{:label :renting  :change-keys [:rent-informations]}
                                                  {:label :selling  :change-keys [:brands]}
                                                  {:label :webshop  :change-keys [:webshop-link]}
                                                  {:label :contacts :change-keys [:address-data-information :contacts-data-information]}
                                                  {:label :about-us :change-keys [:about-us]}]
                                     :disabled? editor-disabled?}]))

(defn- body
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-content.editor])]
       (case current-view-id :renting  [renting]
                             :selling  [selling]
                             :webshop  [webshop]
                             :contacts [contacts]
                             :about-us [about-us])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- controls
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/file-editor-controls :website-content.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-breadcrumbs :website-content.editor/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :website-content}]
                                    :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/surface-label :website-content.editor/view
                             {:disabled? editor-disabled?
                              :label     :website-content}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [:<> [:div {:style {:display "flex" :justify-content "space-between" :flex-wrap "wrap" :grid-row-gap "48px"}}
             [:div [label]
                   [breadcrumbs]]
             [:div [controls]]]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:<> [header]
       [body]])

(defn- website-content-editor
  []
  [file-editor/body :website-content.editor
                    {:content-path  [:website-content :editor/edited-item]
                     :form-element  #'view-structure
                     :error-element [common/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element #'common/file-editor-ghost-element}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-content-editor}])
