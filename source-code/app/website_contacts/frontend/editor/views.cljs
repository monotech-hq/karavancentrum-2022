
(ns app.website-contacts.frontend.editor.views
    (:require [app.common.frontend.api                    :as common]
              [app.components.frontend.api                :as components]
              [app.website-contacts.frontend.editor.boxes :as editor.boxes]
              [elements.api                               :as elements]
              [engines.file-editor.api                    :as file-editor]
              [layouts.surface-a.api                      :as surface-a]
              [re-frame.api                               :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts-data
  []
  [:<> [editor.boxes/contacts-data-box]
       [editor.boxes/contacts-data-information-box]
       [editor.boxes/contact-group-list]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- address-data
  []
  [:<> [editor.boxes/address-data-box]
       [editor.boxes/address-data-information-box]
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
  (let [current-view-id @(r/subscribe [:x.gestures/get-current-view-id :website-contacts.editor])]
       (case current-view-id :contacts-data [contacts-data]
                             :address-data  [address-data]
                             :social-media  [social-media])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-contacts.editor])]
       [common/file-editor-menu-bar :website-contacts.editor
                                    {:menu-items [{:label :contacts-data :change-keys [:contact-groups :contacts-data-information]}
                                                  {:label :address-data  :change-keys [:address-groups :address-data-information]}
                                                  {:label :social-media  :change-keys [:facebook-links :instagram-links :youtube-links]}]
                                     :disabled? editor-disabled?}]))

(defn- controls
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-contacts.editor])]
       [common/file-editor-controls :website-contacts.editor
                                    {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-contacts.editor])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :website-contacts}]
                                        :disabled? editor-disabled?}]))

(defn- label
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-contacts.editor])]
       [components/surface-label ::label
                                 {:disabled? editor-disabled?
                                  :label     :website-contacts}]))

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

(defn- website-contacts-editor
  []
  [file-editor/body :website-contacts.editor
                    {:content-path  [:website-contacts :editor/edited-item]
                     :form-element  [view-structure]
                     :error-element [components/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element [components/ghost-view    {:breadcrumb-count 2 :layout :box-surface}]}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-contacts-editor}])
