
(ns app.user.frontend.create-account.views
    (:require [x.app-core.api     :as a]
              [x.app-elements.api :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- create-account-form
  [])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- logged-in-form
  [])
  
;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- body
  []
  (let [viewport-small? @(a/subscribe [:environment/viewport-small?])]
       [:div#create-account--body {:style (if viewport-small? {:width         "320px"}
                                                              {:border-color  "var( --border-color-highlight )"
                                                               :border-radius "var( --border-radius-m )"
                                                               :border-style  "solid"
                                                               :border-width  "1px"
                                                               :width         "320px"})}
                                  (if-let [user-identified? @(a/subscribe [:user/user-identified?])]
                                          [logged-in-form]
                                          [create-account-form])]))

(defn- create-account
  []
  [elements/label {:content "Create account"}])

(defn view
  [surface-id]
  [create-account])
