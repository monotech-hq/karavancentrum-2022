
(ns app.website-config.frontend.editor.views
    (:require [app.common.frontend.api                  :as common]
              [app.components.frontend.api              :as components]
              [app.website-config.frontend.editor.boxes :as editor.boxes]
              [elements.api                             :as elements]
              [engines.file-editor.api                  :as file-editor]
              [layouts.surface-a.api                    :as surface-a]
              [re-frame.api                             :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- seo
  []
  [:<> [editor.boxes/seo-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- share
  []
  [:<> [editor.boxes/share-preview-box]])

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
