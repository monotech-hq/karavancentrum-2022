
(ns app.website-impressum.frontend.editor.views
    (:require [app.common.frontend.api                     :as common]
              [app.components.frontend.api                 :as components]
              [app.storage.frontend.api                    :as storage]
              [app.website-impressum.frontend.editor.boxes :as editor.boxes]
              [elements.api                                :as elements]
              [engines.file-editor.api                     :as file-editor]
              [layouts.surface-a.api                       :as surface-a]
              [re-frame.api                                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- basic-data
  []
  [:<> [editor.boxes/company-logo-box]
       [editor.boxes/company-data-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts-data
  []
  [:<> [editor.boxes/contacts-data-box]
       [editor.boxes/contact-group-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- address-data
  []
  [:<> [editor.boxes/address-data-box]
       [editor.boxes/address-group-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- social-media
  []
  [:<> [editor.boxes/social-media-box]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- body
  []
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-impressum.editor])]
       (case current-view-id :basic-data    [basic-data]
                             :contacts-data [contacts-data]
                             :address-data  [address-data]
                             :social-media  [social-media])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [common/file-editor-menu-bar :website-impressum.editor
                                    {:menu-items [{:label :basic-data    :change-keys [:company-name :company-slogan :company-logo]}
                                                  {:label :contacts-data :change-keys [:contact-groups]}
                                                  {:label :address-data  :change-keys [:address-groups]}
                                                  {:label :social-media  :change-keys [:facebook-links :instagram-links :youtube-links]}]
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
