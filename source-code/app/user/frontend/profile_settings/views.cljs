
(ns app.user.frontend.profile-settings.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [re-frame.api            :as r]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- body
  []
  [elements/label {:color     :highlight
                   :content   :there-is-no-available-settings
                   :font-size :xs
                   :indent    {:top :l :vertical :xs}}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  [common/surface-breadcrumbs :user.profile-settings/view
                              {:crumbs [{:label :app-home :route "/@app-home"}
                                        {:label :user-profile}]}])

(defn- label
  []
  [common/surface-label :user.profile-settings/view
                        {:label :user-profile}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) surface-id
  [_]
  (if-let [page-loaded? @(r/subscribe [:db/get-item [:user :profile-settings/page-loaded?]])]
          [:<> [label]
               [breadcrumbs]
               [body]]
          [common/surface-box-layout-ghost-view :user.profile-settings/view {:breadcrumb-count 1}]))

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content [view-structure surface-id]}])
