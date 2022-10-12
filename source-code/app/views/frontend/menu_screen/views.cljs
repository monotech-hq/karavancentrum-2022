
(ns app.views.frontend.menu-screen.views
    (:require [layouts.popup-a.api :as popup-a]
              [mid-fruits.css      :as css]
              [re-frame.api        :as r]
              [x.app-details       :as details]
              [x.app-elements.api  :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-id->parent-view-id
  [view-id]
  (case view-id :language-selector :main
                :more-options      :main
                :about-app         :more-options))

(defn- back-button
  []
  (let [view-id       @(r/subscribe [:gestures/get-current-view-id :views.menu-screen/handler])
        parent-view-id (view-id->parent-view-id view-id)]
       [elements/button ::back-button
                        {:hover-color :highlight
                         :indent      {:vertical :xs}
                         :on-click    [:gestures/change-view! :views.menu-screen/handler parent-view-id]
                         :preset      :back}]))

;; -- Language selector components --------------------------------------------
;; ----------------------------------------------------------------------------

(defn- language-button
  [language-key]
  (let [selected-language @(r/subscribe [:locales/get-selected-language])
        language-selected? (= language-key selected-language)]
       [elements/button {:hover-color :highlight
                         :icon        (if language-selected? :radio_button_checked :radio_button_unchecked)
                         :label       language-key
                         :indent      {:vertical :xs}
                         :on-click    [:settings.handler/update-user-settings! {:selected-language language-key}]
                         :preset      :default}]))

(defn- language-list
  []
  (letfn [(f [language-key] ^{:key (str "x-app-menu--languages--" language-key)}
                             [language-button language-key])]
         (let [app-languages @(r/subscribe [:locales/get-app-languages])]
              [:div#x-app-menu--languages (map f app-languages)])))

(defn- language-selector
  []
  [:<> ;[elements/horizontal-separator {:size :l}]
       ; TEMP
       [elements/button {:disabled?   true
                         :hover-color :highlight
                         :icon        :radio_button_unchecked
                         :indent      {:vertical :xs}
                         :label       :fr
                         :preset      :muted}]
       ; TEMP
       [elements/button {:disabled?   true
                         :hover-color :highlight
                         :icon        :radio_button_unchecked
                         :indent      {:vertical :xs}
                         :label       :de
                         :preset      :muted}]
       [language-list]
       [back-button]])

(defn- language-selector-button
  []
  (let [app-multilingual? @(r/subscribe [:locales/app-multilingual?])]
       [elements/button ::language-selector-button
                        {:hover-color :highlight
                         :indent      {:vertical :xs}
                         :on-click    [:gestures/change-view! :views.menu-screen/handler :language-selector]
                         :preset      :language
                         :disabled?   (not app-multilingual?)}]))
                         ; TEMP
                         ;:disabled? true

;; -- Main view components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- user-profile-button
  []
  [elements/button ::user-profile-button
                   {:hover-color :highlight
                    :indent      {:vertical :xs}
                    :on-click    [:router/go-to! "/@app-home/user-profile"]
                    :preset      :user-profile}])

(defn- settings-button
  []
  [elements/button ::settings-button
                   {:hover-color :highlight
                    :indent      {:vertical :xs}
                    :on-click    [:router/go-to! "/@app-home/settings"]
                    :preset      :settings}])

(defn- more-options-button
  []
  [elements/button ::more-options-button
                   {:hover-color :highlight
                    :indent      {:vertical :xs}
                    :on-click    [:gestures/change-view! :views.menu-screen/handler :more-options]
                    :preset      :more-options}])

(defn- logout-button
  []
  [elements/button ::logout-button
                   {:hover-color :highlight
                    :indent      {:vertical :xs}
                    :on-click    [:user/logout!]
                    :preset      :logout}])

(defn- main
  []
  [:<> [language-selector-button]
       [user-profile-button]
       [settings-button]
       [more-options-button]
       [logout-button]])

;; -- About-app view components -----------------------------------------------
;; ----------------------------------------------------------------------------

(defn- app-description-label
  []
  [elements/label ::app-description-label
                  {:content          (str details/app-codename " | " details/app-description)
                   :color            :muted
                   :horizontal-align :left
                   :icon             :grade
                  ;:icon-family      :material-icons-outlined
                   :indent           {:horizontal :xxs :vertical :s}}])

(defn- app-version-label
  []
  [elements/label ::app-version-label
                  {:content          (str "v"details/app-version)
                   :color            :muted
                   :horizontal-align :left
                   :icon             :extension
                  ;:icon-family      :material-icons-outlined
                   :indent           {:horizontal :xxs :vertical :s}}])

(defn- copyright-information-label
  []
  (let [server-year    @(r/subscribe [:core/get-server-year])
        copyright-label (details/copyright-label server-year)]
       [elements/label ::copyright-information-label
                       {:content          copyright-label
                        :color            :muted
                        :horizontal-align :left
                        :icon             :copyright
                        :indent           {:horizontal :xxs :vertical :s}}]))

(defn- about-app
  []
  [:<> [elements/horizontal-separator {:size :l}]
       [app-description-label]
       [app-version-label]
       [copyright-information-label]
       [back-button]])

;; -- More options view components --------------------------------------------
;; ----------------------------------------------------------------------------

(defn- terms-of-service-button
  []
  [elements/button ::terms-of-service-button
                   {:hover-color :highlight
                    :icon        :subject
                    :indent      {:vertical :xs}
                    :label       :terms-of-service
                    :on-click    [:router/go-to! "/@app-home/terms-of-service"]
                    :preset      :default}])

(defn- privacy-policy-button
  []
  [elements/button ::privacy-policy-button
                   {:hover-color :highlight
                    :icon        :privacy_tip
                    :icon-family :material-icons-outlined
                    :indent      {:vertical :xs}
                    :label       :privacy-policy
                    :on-click    [:router/go-to! "/@app-home/privacy-policy"]
                    :preset      :default}])

(defn- about-app-button
  []
  [elements/button ::about-app-button
                   {:hover-color :highlight
                    :icon        :copyright
                    :indent      {:vertical :xs}
                    :label       :about-app
                    :on-click    [:gestures/change-view! :views.menu-screen/handler :about-app]
                    :preset      :default}])

(defn- more-options
  []
  [:<> [elements/horizontal-separator {:size :l}]
       [privacy-policy-button]
       [terms-of-service-button]
       [about-app-button]
       [back-button]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- app-menu
  []
  (let [view-id @(r/subscribe [:gestures/get-current-view-id :views.menu-screen/handler])]
       (case view-id :about-app         [about-app]
                     :language-selector [language-selector]
                     :main              [main]
                     :more-options      [more-options])))

(defn- user-profile-picture
  []
  (let [user-profile-picture @(r/subscribe [:user/get-user-profile-picture])]
       [:div.x-user-profile-picture {:style {:backgroundImage     (css/url user-profile-picture)
                                             :background-color    (css/var "background-color-highlight")
                                             :border-radius       "50%";
                                             :background-position "center"
                                             :background-repeat   "no-repeat"
                                             :background-size     "90%"
                                             :overflow            "hidden"
                                             :height              "80px"
                                             :width               "80px"}}]))

(defn- user-name-label
  []
  (let [user-first-name @(r/subscribe [:user/get-user-first-name])
        user-last-name  @(r/subscribe [:user/get-user-last-name])
        user-full-name  @(r/subscribe [:locales/get-ordered-name user-first-name user-last-name])]
       [elements/label ::user-name-label
                       {:content     user-full-name
                        :font-size   :s
                        :font-weight :extra-bold}]))

(defn- user-email-address-label
  []
  (let [user-email-address @(r/subscribe [:user/get-user-email-address])]
       [elements/label ::user-email-address-label
                       {:color     :muted
                        :content   user-email-address
                        :font-size :xs}]))

(defn- user-card
  []
  [elements/column ::user-card
                   {:content [:<> [user-profile-picture]
                                  [elements/horizontal-separator {:size :s}]
                                  [user-name-label]
                                  [user-email-address-label]]
                    :indent {:bottom :m}
                    :stretch-orientation :horizontal}])

(defn- body
  []
  [:<> [user-card]
       [app-menu]
       [elements/horizontal-separator {:size :s}]])

(defn- close-icon-button
  []
  [elements/icon-button ::close-icon-button
                        {:border-radius :s
                         :hover-color   :highlight
                         :keypress      {:key-code 27}
                         :on-click      [:ui/close-popup! :views.menu-screen/view]
                         :preset        :close}])

(defn- header
  []
  [elements/horizontal-polarity ::header
                                {:end-content [close-icon-button]}])

(defn view
  [popup-id]
  [popup-a/layout popup-id
                  {:body      #'body
                   :header    #'header
                   :min-width :xs}])
