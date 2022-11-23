
(ns app.website-menu.frontend.editor.views
    (:require [app.common.frontend.api                :as common]
              [app.components.frontend.api            :as components]
              [app.website-menu.frontend.editor.boxes :as editor.boxes]
              [elements.api                           :as elements]
              [engines.file-editor.api                :as file-editor]
              [layouts.surface-a.api                  :as surface-a]
              [re-frame.api                           :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- website-menu
  []
  [:<> [editor.boxes/website-menu-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- body
  []
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-menu.editor])]
       (case current-view-id :website-menu [website-menu])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-menu.editor])]
       [common/file-editor-menu-bar :website-menu.editor
                                    {:menu-items [{:label :website-menu :change-keys [:menu-items]}]
                                     :disabled? editor-disabled?}]))

(defn- controls
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-menu.editor])]
       [common/file-editor-controls :website-menu.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-menu.editor])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :website-menu}]
                                        :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-menu.editor])]
       [components/surface-label ::label
                                 {:disabled? editor-disabled?
                                  :label     :website-menu}]))

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

(defn- website-menu-editor
  []
  [file-editor/body :website-menu.editor
                    {:content-path  [:website-menu :editor/edited-item]
                     :form-element  [view-structure]
                     :error-element [components/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element [components/ghost-view    {:breadcrumb-count 2 :layout :box-surface}]}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-menu-editor}])
