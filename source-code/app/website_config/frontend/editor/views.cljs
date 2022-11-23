
(ns app.website-config.frontend.editor.views
    (:require [app.common.frontend.api     :as common]
              [app.components.frontend.api :as components]
              [app.storage.frontend.api    :as storage]
              [css.api                     :as css]
              [elements.api                :as elements]
              [engines.file-editor.api     :as file-editor]
              [forms.api                   :as forms]
              [layouts.surface-a.api       :as surface-a]
              [re-frame.api                :as r]
              [vector.api                  :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- meta-name-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [elements/text-field ::meta-name-field
                            {:autofocus?  true
                             :disabled?   editor-disabled?
                             :label       :meta-name
                             :indent      {:top :m :vertical :s}
                             :info-text   :describe-the-page-with-a-name
                             :placeholder :meta-name-placeholder
                             :value-path  [:website-config :editor/edited-item :meta-name]}]))

(defn- meta-title-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [elements/text-field ::meta-title-field
                            {:disabled?   editor-disabled?
                             :label       :meta-title
                             :indent      {:top :m :vertical :s}
                             :info-text   :describe-the-page-with-a-short-title
                             :placeholder :meta-title-placeholder
                             :value-path  [:website-config :editor/edited-item :meta-title]}]))

(defn- meta-description-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [elements/multiline-field ::meta-description-field
                                 {:disabled?   editor-disabled?
                                  :label       :meta-description
                                  :indent      {:top :m :vertical :s}
                                  :info-text   :describe-the-page-with-a-short-description
                                  :placeholder :meta-description-placeholder
                                  :value-path  [:website-config :editor/edited-item :meta-description]}]))

(defn- meta-keywords-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [elements/multi-combo-box ::meta-keywords-field
                                 {:deletable?  true
                                  :disabled?   editor-disabled?
                                  :label       :meta-keywords
                                  :indent      {:top :m :vertical :s}
                                  :info-text   :describe-the-page-in-a-few-keywords
                                  :placeholder :meta-keywords-placeholder
                                  :value-path  [:website-config :editor/edited-item :meta-keywords]}]))

(defn- seo-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [components/surface-box ::seo-box
                               {:content [:<> [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [meta-name-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [meta-title-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [meta-keywords-field]]
                                               [:div (forms/form-row-attributes)
                                                     [:div (forms/form-block-attributes {:ratio 100})
                                                           [meta-description-field]]]]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :label     :seo}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- seo
  []
  [:<> [seo-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- share-preview-picker
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [storage/media-picker ::share-preview-picker
                             {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:vertical :s}
                              :multi-select? false
                              :placeholder   "-"
                              :toggle-label  :select-image!
                              :value-path    [:website-config :editor/edited-item :share-preview]}]))

(defn- share-preview-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [components/surface-box ::share-preview-box
                               {:content [:<> [share-preview-picker]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :info-text {:content :recommended-image-size-n :replacements ["1200" "630"]}
                                :label     :share-preview}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- share
  []
  [:<> [share-preview-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- body
  []
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-config.editor])]
       (case current-view-id :seo   [seo]
                             :share [share])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [common/file-editor-menu-bar :website-config.editor
                                    {:menu-items [{:label :seo   :change-keys [:meta-name :meta-title :meta-keywords :meta-description]}
                                                  {:label :share :change-keys [:share-preview]}]
                                     :disabled? editor-disabled?}]))

(defn- controls
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [common/file-editor-controls :website-config.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :website-config}]
                                        :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-config.editor])]
       [components/surface-label ::label
                                 {:disabled? editor-disabled?
                                  :label     :website-config}]))

(defn- header
  []
  [:<> [:div {:style {:display "flex" :justify-content "space-between" :flex-wrap "wrap" :grid-row-gap "48px"}}
             [:div [label]
                   [breadcrumbs]]
             [:div [controls]]]
       [elements/horizontal-separator {:height :xxl}]
       [menu-bar]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [header]
       [body]])

(defn- website-config-editor
  []
  [file-editor/body :website-config.editor
                    {:content-path  [:website-config :editor/edited-item]
                     :form-element  [view-structure]
                     :error-element [components/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element [components/ghost-view    {:breadcrumb-count 2 :layout :box-surface}]}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-config-editor}])
