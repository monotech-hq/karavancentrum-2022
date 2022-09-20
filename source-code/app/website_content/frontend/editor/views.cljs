
(ns app.website-content.frontend.editor.views
    (:require [app.storage.frontend.api]
              [app.common.frontend.api :as common]
              [forms.api               :as forms]
              [layouts.surface-a.api   :as surface-a]
              [mid-fruits.css          :as css]
              [mid-fruits.vector       :as vector]
              [plugins.file-editor.api :as file-editor]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  [common/file-editor-menu-bar :website-content
                               {:menu-items [{:change-keys [:company-name :company-slogan :company-logo]
                                              :label   :basic-info
                                              :view-id :basic-info}
                                             {:change-keys [:contact-groups]
                                              :label   :contacts-data
                                              :view-id :contacts-data}
                                             {:change-keys [:address-groups]
                                              :label   :address-data
                                              :view-id :address-data}
                                             {:change-keys [:facebook-links :instagram-links :youtube-links]
                                              :label   :social-media
                                              :view-id :social-media}
                                             {:change-keys [:meta-name :meta-title :meta-keywords :meta-description]
                                              :label   :seo
                                              :view-id :seo}
                                             {:change-keys [:share-preview]
                                              :label   :share
                                              :view-id :share}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-content])]
       [:<> [menu-bar]]))
            ;(case current-view-id :basic-info    [basic-info]
            ;                      :contacts-data [contacts-data]
            ;                      :address-data  [address-data]
            ;                      :social-media  [social-media]
            ;                      :seo           [seo]
            ;                      :share         [share]]]))

(defn- website-content-editor
  []
  [file-editor/body :website-content
                    {:content-path   [:website-content :content-editor/edited-item]
                     :form-element  #'view-selector
                     :ghost-element #'common/file-editor-ghost-view}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  [common/file-editor-breadcrumbs :website-content
                                  {:crumbs [{:label :app-home
                                             :route "/@app-home"}
                                            {:label :website-content}]}])

(defn- label-bar
  []
  [common/file-editor-label-bar :website-content
                                {:label :website-content}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [website-content-editor]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
