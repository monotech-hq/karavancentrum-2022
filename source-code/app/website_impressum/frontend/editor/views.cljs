
(ns app.website-impressum.frontend.editor.views
    (:require [app.common.frontend.api                     :as common]
              [app.components.frontend.api                 :as components]
              [app.website-impressum.frontend.editor.boxes :as editor.boxes]
              [elements.api                                :as elements]
              [engines.file-editor.api                     :as file-editor]
              [layouts.surface-a.api                       :as surface-a]
              [re-frame.api                                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- company-data
  []
  [:<> [editor.boxes/company-data-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- body
  []
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-impressum.editor])]
       (case current-view-id :company-data [company-data])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [common/file-editor-menu-bar :website-impressum.editor
                                    {:menu-items [{:label :company-data :change-keys [:company-name :company-est-year
                                                                                      :company-reg-office :company-reg-no
                                                                                      :company-vat-no :company-eu-vat-no]}]
                                     :disabled? editor-disabled?}]))

(defn- controls
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [common/file-editor-controls :website-impressum.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :website-impressum}]
                                        :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [components/surface-label ::label
                                 {:disabled? editor-disabled?
                                  :label     :website-impressum}]))

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

(defn- website-impressum-editor
  []
  [file-editor/body :website-impressum.editor
                    {:content-path  [:website-impressum :editor/edited-item]
                     :form-element  [view-structure]
                     :error-element [components/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element [components/ghost-view    {:breadcrumb-count 2 :layout :box-surface}]}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-impressum-editor}])
