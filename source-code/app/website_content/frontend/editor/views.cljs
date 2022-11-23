
(ns app.website-content.frontend.editor.views
    (:require [app.common.frontend.api                   :as common]
              [app.components.frontend.api               :as components]
              [app.contents.frontend.api                 :as contents]
              [app.website-content.frontend.editor.boxes :as editor.boxes]
              [elements.api                              :as elements]
              [engines.file-editor.api                   :as file-editor]
              [layouts.surface-a.api                     :as surface-a]
              [re-frame.api                              :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- about-us
  []
  [:<> [editor.boxes/about-us-section-box]
       [editor.boxes/about-us-page-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- webshop
  []
  [:<> [editor.boxes/webshop-settings-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- selling
  []
  [:<> [editor.boxes/selling-box]
       [editor.boxes/brand-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- renting
  []
  [:<> [editor.boxes/rent-informations-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/file-editor-menu-bar :website-content.editor
                                    {:menu-items [{:label :renting  :change-keys [:rent-informations]}
                                                  {:label :selling  :change-keys [:brands]}
                                                  {:label :webshop  :change-keys [:webshop-link]}
                                                  {:label :about-us :change-keys [:about-us-section :about-us-page]}]
                                     :disabled? editor-disabled?}]))

(defn- body
  []
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-content.editor])]
       (case current-view-id :renting  [renting]
                             :selling  [selling]
                             :webshop  [webshop]
                             :about-us [about-us])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- controls
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [common/file-editor-controls :website-content.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :website-content}]
                                        :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-content.editor])]
       [components/surface-label ::label
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
       [elements/horizontal-separator {:height :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:<> [header]
       [body]])

(defn- website-content-editor
  ; @param (keyword) surface-id
  [_]
  [file-editor/body :website-content.editor
                    {:content-path  [:website-content :editor/edited-item]
                     :form-element  [view-structure]
                     :error-element [components/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element [components/ghost-view    {:breadcrumb-count 2 :layout :box-surface}]}])

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-content-editor}])
